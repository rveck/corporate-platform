package br.com.veck.customerservice.controller;

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

import br.com.veck.customerservice.model.Customer;
import br.com.veck.customerservice.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {
		
	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Test
	public void create_customer_should_return_404_status_code() {
		//setup
		when(customerRepository.save(Mockito.any())).thenReturn(null);
		
		//test
		assertThat(customerController.create(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);				
	}
	
	@Test
	public void create_customer_should_return_200_status_code() {
		//setup
		when(customerRepository.save(Mockito.any())).thenReturn(new Customer());
		
		//test
		assertThat(customerController.create(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void consult_customer_should_return_404_status_code() throws Exception {
		//setup
		when(customerRepository.findOneById(Mockito.any())).thenReturn(null);
		
		//test
		assertThat(customerController.consult(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);				
	}
	
	@Test
	public void consult_customer_should_return_500_status_code() throws Exception {
		//setup
		when(customerRepository.findOneById(Mockito.any())).thenThrow(new Exception());
		
		//test
		assertThat(customerController.consult(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);				
	}
	
	@Test
	public void consult_customer_should_return_200_status_code() throws Exception {
		//setup
		when(customerRepository.findOneById(Mockito.any())).thenReturn(new Customer());
		
		//test
		assertThat(customerController.consult(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void consult_all_customer_should_return_404_status_code() throws Exception {
		//setup
		when(customerRepository.findAll()).thenReturn(null);
		
		//test
		assertThat(customerController.consultAll().getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);				
	}
	
	@Test
	public void consult_all_customer_should_return_200_status_code() throws Exception {
		//setup
		when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer()));
		
		//test
		assertThat(customerController.consultAll().getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void update_customer_should_return_404_status_code() throws Exception {
		//setup
		when(customerRepository.save(Mockito.any())).thenReturn(null);
		
		//test
		assertThat(customerController.update(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);				
	}
	
	@Test
	public void update_customer_should_return_200_status_code() throws Exception {
		//setup
		when(customerRepository.save(Mockito.any())).thenReturn(new Customer());
		
		//test
		assertThat(customerController.update(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void delete_customer_should_return_404_status_code() throws Exception {
		//setup
		when(customerRepository.findOneById(Mockito.any())).thenReturn(null);
		
		//test
		assertThat(customerController.delete(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);				
	}
	
	@Test
	public void delete_customer_should_return_200_status_code() throws Exception {
		//setup
		when(customerRepository.findOneById(Mockito.any())).thenReturn(new Customer());
		
		//test
		assertThat(customerController.delete(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);				
	}
	
	@Test
	public void delete_customer_should_return_500_status_code() throws Exception {
		//setup
		when(customerRepository.findOneById(Mockito.any())).thenThrow(new Exception());
		
		//test
		assertThat(customerController.delete(Mockito.any()).getStatusCode()).isEqualByComparingTo(HttpStatus.INTERNAL_SERVER_ERROR);				
	}

}
