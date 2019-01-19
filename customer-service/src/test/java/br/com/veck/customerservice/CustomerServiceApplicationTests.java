package br.com.veck.customerservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.veck.customerservice.controller.CustomerController;
import br.com.veck.customerservice.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceApplicationTests {
	
	@Autowired
    private CustomerController customerController;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	public void contextLoads() {
		assertThat(customerController).isNotNull();
		assertThat(customerRepository).isNotNull();
	}
}

