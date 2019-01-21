package br.com.veck.customerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.veck.customerservice.repository.CustomerRepository;
import br.com.veck.model.Customer;
import br.com.veck.model.Geolocalization;
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
		customer.setGeolocalization(getGeolocalization(customer.getIp()));
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
	
	public Geolocalization getGeolocalization(String ip) {
		Geolocalization geolocalization = null;
		try {			
			String url = String.format(uriApiGeolocalization, ip);			
			String json = restTemplate.getForObject(url, String.class);
			IpVigilanteResponse ipVigilanteResponse = Util.convertJsonToObject(json, IpVigilanteResponse.class);			
			
			if (ipVigilanteResponse != null && 
					ipVigilanteResponse.getStatus() != null && 
					ipVigilanteResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
				geolocalization = ipVigilanteResponse.getData();
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return geolocalization;
	}
}
