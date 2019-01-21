package br.com.veck.apiclient;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.veck.apiclient.controller.CustomerApiController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiClientApplicationTests {
	
	@Autowired
	private CustomerApiController customerApiController;

	@Test
	public void contextLoads() {
		assertThat(customerApiController).isNotNull();
	}
}

