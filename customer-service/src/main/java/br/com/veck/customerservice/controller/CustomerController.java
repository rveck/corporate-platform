package br.com.veck.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.veck.customerservice.model.Customer;
import br.com.veck.customerservice.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer create(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer consult(@PathVariable String id) {
		return customerRepository.findOneById(id);
	}
	
	@GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> consultAll() {
		return customerRepository.findAll();
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer update(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable String id) {
		customerRepository.deleteById(id);
	}
}
