package br.com.veck.customerservice.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.veck.customerservice.repository.CustomerRepository;
import br.com.veck.model.Customer;
import br.com.veck.model.Geolocalization;
import br.com.veck.model.Location;
import br.com.veck.model.Weather;
import br.com.veck.pojo.IpVigilanteResponse;
import br.com.veck.util.Constants;
import br.com.veck.util.Util;

@Service
public class CustomerService {
	
	@Value("${uri.api.geolocalization}")
	private String uriApiGeolocalization;
	
	@Value("${uri.api.weather.search.location}")
	private String uriApiWeatherSearchLocation;
	
	@Value("${uri.api.weather.location}")
	private String uriApiWeatherLocation;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	// for tests
	//private static final String WEATHER_DATE_FIELD = "created";
	//private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
	
	private static final String WEATHER_DATE_FIELD = "applicable_date";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String INVALID_IP = "0:0:0:0:0:0:0:1";
	
	public Customer save(Customer customer) {
		Optional<Geolocalization> geolocalization = Optional.empty();
		Optional<Weather> weather = Optional.empty();
		
		Optional<String> ip = Optional.ofNullable(customer.getIp());
		if (ip.isPresent() && !ip.get().equals(INVALID_IP)) {
			geolocalization = this.getGeolocalization(ip);
			if (geolocalization.isPresent()) {
				customer.setGeolocalization(geolocalization.get());
				weather = this.getWeather(geolocalization.get().getLatitude(), 
						geolocalization.get().getLongitude());			
			}
			
			if (weather.isPresent()) {
				customer.setWeather(weather.get());
			}
		}		
		return customerRepository.save(customer);
	}
	
	public Customer findOneById(String id) throws Exception {
		return customerRepository.findOneById(id);
	}
	
	public List<Customer> findAll() throws Exception {
		return customerRepository.findAll();
	}
	
	public void deleteById(String id) {
		customerRepository.deleteById(id);
	}
	
	public Optional<Geolocalization> getGeolocalization(Optional<String> ip) {
		Optional<Geolocalization> geolocalization = Optional.empty();
		try {			
			String url = String.format(uriApiGeolocalization, ip.get());			
			String json = restTemplate.getForObject(url, String.class);
			IpVigilanteResponse ipVigilanteResponse = Util.convertJsonToObject(json, IpVigilanteResponse.class);			
			
			if (ipVigilanteResponse != null && 
					ipVigilanteResponse.getStatus() != null && 
					ipVigilanteResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
				geolocalization = Optional.of(ipVigilanteResponse.getData());
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return geolocalization;
	}
	
	public Optional<Weather> getWeather(String lat, String lon) {
		Optional<Weather> weather = Optional.empty();
		Date today = new Date();
		try {
			Optional<Long> woeid = getWoeid(lat, lon);
			if (woeid.isPresent()) {
				String searchLocationUrl = String.format(uriApiWeatherLocation, woeid.get());
				String json = restTemplate.getForObject(searchLocationUrl, String.class);
				List<Weather> lstWeather = jsonToListWeather(today, json);
				return getMaxMinTemp(today, lstWeather);
			}						
		}catch(Exception e) {
			e.printStackTrace();
		}
		return weather;
	}
	
	public List<Weather> jsonToListWeather(Date today, String json) throws JSONException, ParseException{		
		List<Weather> lstWeather = new ArrayList<Weather>();
		JSONObject jsonObject = new JSONObject(json);
		JSONArray consolidatedWeathers = jsonObject.getJSONArray("consolidated_weather");
		for (int i = 0; i < consolidatedWeathers.length(); i++) {
			JSONObject weatherJson =  consolidatedWeathers.getJSONObject(i);
			Date weatherDate = sdf.parse(weatherJson.getString(WEATHER_DATE_FIELD));
			if (Util.isSameDay(weatherDate,today)) {			
				Weather weather = 
						new Weather(
								weatherDate, 
								new BigDecimal(weatherJson.getDouble("min_temp")), 
								new BigDecimal(weatherJson.getDouble("max_temp"))
						);			
				lstWeather.add(weather);
				weather = null;
			}
		}
		return lstWeather;
	}
	
	public Optional<Weather> getMaxMinTemp(Date date, List<Weather> lstWeathers){
		Optional<Weather> weather = Optional.empty();
		
		if (lstWeathers.size() > 0) {						
			BigDecimal maxTemp = lstWeathers
				      .stream()
				      .max(Comparator.comparing(Weather::getMaxTemp))
				      .orElseThrow(NoSuchElementException::new).getMaxTemp();	
			
			BigDecimal minTemp = lstWeathers
				      .stream()
				      .min(Comparator.comparing(Weather::getMinTemp))
				      .orElseThrow(NoSuchElementException::new).getMinTemp();	
			Weather w = new Weather(date, minTemp, maxTemp);
			weather = Optional.of(w);
		}
		
		return weather;
	}
	
	public Optional<Long> getWoeid(String lat, String lon) {
		Optional<Long> woeid = Optional.empty();
		try {
			String searchLocationUrl = String.format(uriApiWeatherSearchLocation, lat, lon);
			String json = restTemplate.getForObject(searchLocationUrl, String.class);
			JSONArray jsonArray = new JSONArray(json);
			List<Location> lstLocations = new ArrayList<Location>();
			for (int i = 0; i < jsonArray.length(); i++) {				
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Location location = new Location(
						jsonObject.getLong("distance"), 
						jsonObject.getLong("woeid")
				);
				lstLocations.add(location);
			}
			woeid = Optional.of(minLocationValue(lstLocations).getWoeid());						
		}catch(Exception e) {
			e.printStackTrace();
		}
		return woeid;
		
	} 
	
	public Location minLocationValue(List<Location> lstLocations) {
		return lstLocations
	      .stream()
	      .min(Comparator.comparing(Location::getDistance))
	      .orElseThrow(NoSuchElementException::new);
	}
}
