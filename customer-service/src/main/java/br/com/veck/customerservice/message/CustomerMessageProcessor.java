package br.com.veck.customerservice.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.veck.customerservice.controller.CustomerController;
import br.com.veck.model.Customer;
import br.com.veck.util.Util;

@Component
public class CustomerMessageProcessor {
	
	@Autowired
	private CustomerController customerController;
	
	public void processMessage(String message) {
		Customer customer = Util.convertJsonToObject(message, Customer.class);				
		
		switch (customer.getOperation()){
			case "create":{				
				customerController.create(customer);
				break;
			}
			case "update":{				
				customerController.update(customer);
				break;
			}
			case "delete":{
				customerController.delete(customer.getId());
				break;
			}
		}
	}
}
