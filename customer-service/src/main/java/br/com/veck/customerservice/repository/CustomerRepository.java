package br.com.veck.customerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.veck.customerservice.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>{
	
	public Customer findOneById(String id);

}
