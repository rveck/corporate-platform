package br.com.veck.customerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import br.com.veck.model.Geolocalization;
import br.com.veck.pojo.IpVigilanteResponse;
import br.com.veck.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Before
    public void setUp() {
        ReflectionTestUtils.setField(customerService, "uriApiGeolocalization", "uriApiGeolocalization");
		ReflectionTestUtils.setField(customerService, "uriApiWeatherSearchLocation", "uriApiWeatherSearchLocation");
		ReflectionTestUtils.setField(customerService, "uriApiWeatherLocation", "uriApiWeatherLocation");
    }
	
	@Test
	public void should_return_null_geolocalization_object() {
		
		Geolocalization geolocalization = new Geolocalization();		
		IpVigilanteResponse ipVigilanteResponse = new IpVigilanteResponse();
		ipVigilanteResponse.setStatus(Constants.ERROR);
		ipVigilanteResponse.setData(geolocalization);
		
		//setup
		when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(IpVigilanteResponse.class))).thenReturn(ipVigilanteResponse);
		
		//test
		assertThat(customerService.getGeolocalization(Optional.ofNullable(Mockito.anyString()))).isNull();
	}
	
	@Test
	public void should_return_geolocation_object_not_null() {
		
	}
	
	@Test
	public void should_return_weather_object_not_null() {
		
	}

}
