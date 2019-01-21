package br.com.veck.geolocalizationservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.veck.geolocalizationservice.repository.GeolocalizationRepository;
import br.com.veck.model.Geolocalization;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeolocalizationControllerTest {
	
	@InjectMocks
	private GeolocalizationController geolocalizationController;
	
	@Mock
	private GeolocalizationRepository geolocalizationRepository;
	
	@Test
	public void create_geolicalization_should_return_404_status_code() {
		//setup
		when(geolocalizationRepository.save(Mockito.any())).thenReturn(null);
		
		//test
		assertThat(geolocalizationController.create(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);				
	}
	
	@Test
	public void create_geolicalization_should_return_200_status_code() {
		//setup
		when(geolocalizationRepository.save(Mockito.any())).thenReturn(new Geolocalization());
		
		//test
		assertThat(geolocalizationController.create(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void consult_all_geolicalization_should_return_404_status_code() throws Exception {
		//setup
		when(geolocalizationRepository.findAll()).thenReturn(null);
		
		//test
		assertThat(geolocalizationController.consultAll().getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);				
	}
	
	@Test
	public void consult_all_geolicalization_should_return_200_status_code() throws Exception {
		//setup
		when(geolocalizationRepository.findAll()).thenReturn(Arrays.asList(new Geolocalization()));
		
		//test
		assertThat(geolocalizationController.consultAll().getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void delete_geolicalization_should_return_404_status_code() throws Exception {
		//setup
		when(geolocalizationRepository.findOneById(Mockito.any())).thenReturn(null);
		
		//test
		assertThat(geolocalizationController.delete(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);				
	}
	
	@Test
	public void delete_geolicalization_should_return_200_status_code() throws Exception {
		//setup
		when(geolocalizationRepository.findOneById(Mockito.any())).thenReturn(new Geolocalization());
		
		//test
		assertThat(geolocalizationController.delete(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void delete_geolicalization_should_return_500_status_code() throws Exception {
		//setup
		when(geolocalizationRepository.findOneById(Mockito.any())).thenThrow(new Exception());
		
		//test
		assertThat(geolocalizationController.delete(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);				
	}

}
