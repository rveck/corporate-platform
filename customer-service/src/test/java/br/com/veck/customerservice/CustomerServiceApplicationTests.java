package br.com.veck.customerservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.veck.customerservice.controller.CustomerController;
import br.com.veck.customerservice.message.CustomerMessageProcessor;
import br.com.veck.customerservice.message.MessageConfiguration;
import br.com.veck.customerservice.repository.CustomerRepository;
import br.com.veck.customerservice.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceApplicationTests {
	
	@Autowired
    private CustomerController customerController;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MessageConfiguration messageConfiguration;
	
	@Autowired
	private CustomerMessageProcessor messageProcessor;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
		assertThat(customerController).isNotNull();
		assertThat(customerRepository).isNotNull();
		assertThat(messageConfiguration).isNotNull();
		assertThat(messageProcessor).isNotNull();
		assertThat(customerService).isNotNull();
		assertThat(restTemplate).isNotNull();
	}
}

