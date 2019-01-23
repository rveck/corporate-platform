package br.com.veck.customerservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.internal.LinkedTreeMap;

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
	
	public Customer save(Customer customer) {
		Optional<Geolocalization> geolocalization = Optional.empty();
		Optional<Weather> weather = null;
		
		Optional<String> ip = Optional.ofNullable(customer.getIp());
		if (ip.isPresent()) {
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
		try {
			Optional<Long> woeid = getWoeid(lat, lon);
			if (woeid.isPresent()) {
				String searchLocationUrl = String.format(uriApiWeatherLocation, woeid.get());
				String json = restTemplate.getForObject(searchLocationUrl, String.class);
				JSONObject jsonObject = new JSONObject(json);
				JSONArray consolidatedWeathers = jsonObject.getJSONArray("consolidated_weather");

//TODO terminar
			}						
		}catch(Exception e) {
			e.printStackTrace();
		}
		return weather;
	}
	
	public Optional<Long> getWoeid(String lat, String lon) {
		Optional<Long> woeid = Optional.empty();
		try {
			String searchLocationUrl = String.format(uriApiWeatherSearchLocation, lat, lon);
			String json = restTemplate.getForObject(searchLocationUrl, String.class);
			List<LinkedTreeMap> lst = Util.convertJsonToList(json, LinkedTreeMap.class);
			List<Location> lstLocations = new ArrayList<Location>();
			for (LinkedTreeMap<?, ?> ltm: lst) {				
				long distance = ((Double) ltm.get("distance")).longValue();
				long w = ((Double) ltm.get("woeid")).longValue();
				Location location = new Location(distance, w);
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
	
	public Weather getWeatherOfDay(List<Weather> lstWeathers) {
		return null;
	}
}
