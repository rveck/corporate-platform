package br.com.veck.customerservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.veck.customerservice.service.CustomerService;
import br.com.veck.model.Customer;
import br.com.veck.util.Constants;
import br.com.veck.util.Util;

@RestController
@RequestMapping(Constants.CUSTOMER_SERVICE_ENDPOINT)
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> create(@RequestBody Customer customer, HttpServletRequest request) {
		try {
			String ip = customer.getIp();
			if (ip == null) {
				customer.setIp(Util.getClientIp(request));
			}
			customer = customerService.save(customer);
			if (customer == null) {
				throw new Exception();
			}
			return new ResponseEntity<Customer>(customer,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> consult(@PathVariable String id) {
		try {
			Customer customer = customerService.findOneById(id);
			if (customer == null) {
				return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);	
			}
			return new ResponseEntity<>(customer, HttpStatus.OK);			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> consultAll() {
		try {
			List<Customer> lstCustomers = customerService.findAll();
			if (lstCustomers == null || lstCustomers.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Customer>>(lstCustomers, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> update(@RequestBody Customer customer) {
		customer = customerService.save(customer);
		if (customer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);				
	}
	
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Customer> delete(@PathVariable String id) {
		try {
			Customer customer = customerService.findOneById(id);
			if (customer == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			customerService.deleteById(id);
			return new ResponseEntity<Customer>(HttpStatus.OK);			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
