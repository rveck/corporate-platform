package br.com.veck.apiclient.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.veck.model.Customer;
import br.com.veck.util.Constants;
import br.com.veck.util.Util;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {
	
	@Value("${rabbitmq.customer.exchange}")
	private String rabbitMqExchange;
	
	@Value("${rabbitmq.customer.routingkey}")
	private String rabbitMqRoutingKey;
	
	@Value("${customer.service.uri}")
	private String customerServiceUri;
	
	private final RabbitTemplate rabbitTemplate;
	
	public CustomerApiController(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody Customer customer, HttpServletRequest request) {
		try {
			Optional<String> errorMessage = validateCustomer(customer);
			if (errorMessage.isPresent()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage.get());	
			}else {
				customer.setOperation(Constants.Operations.CREATE.getValue());
				customer.setIp(Util.getClientIp(request));
				rabbitTemplate.convertAndSend(rabbitMqExchange, rabbitMqRoutingKey, Util.convertObjectToJson(customer));			
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody Customer customer) {
		try {
			Optional<String> errorMessage = validateCustomer(customer);
			if (errorMessage.isPresent()) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage.get());
			}else {
				customer.setOperation(Constants.Operations.UPDATE.getValue());
				rabbitTemplate.convertAndSend(rabbitMqExchange, rabbitMqRoutingKey, Util.convertObjectToJson(customer));
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "{" + Constants.ID + "}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		try {
			Customer customer = new Customer();
			customer.setId(id);
			customer.setOperation(Constants.Operations.DELETE.getValue());
			rabbitTemplate.convertAndSend(rabbitMqExchange, rabbitMqRoutingKey, Util.convertObjectToJson(customer));			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Optional<String> validateCustomer(Customer customer) {
		Optional<String> message = Optional.empty();
		
		if (customer.getAge() == null || customer.getAge() == 0) {
			message = Optional.of("O campo 'age' é mandatório");
		}else if (customer.getName() == null || "".equals(customer.getName())) { 
			message = Optional.of("O campo 'name' é mandatório");
		}
		return message;
	}
}
