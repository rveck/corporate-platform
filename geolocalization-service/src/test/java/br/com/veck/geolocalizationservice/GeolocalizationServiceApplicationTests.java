package br.com.veck.geolocalizationservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.veck.geolocalizationservice.controller.GeolocalizationController;
import br.com.veck.geolocalizationservice.repository.GeolocalizationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeolocalizationServiceApplicationTests {
	
	@Autowired
	private GeolocalizationController geolocalizationController;
	
	@Autowired
	private GeolocalizationRepository geolocalizationRepository;
	
	@Test
	public void contextLoads() {
		assertThat(geolocalizationController).isNotNull();
		assertThat(geolocalizationRepository).isNotNull();
	}
}

