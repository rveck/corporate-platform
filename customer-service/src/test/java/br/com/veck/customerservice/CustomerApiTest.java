package br.com.veck.customerservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;

import br.com.veck.customerservice.controller.CustomerController;
import br.com.veck.model.Customer;
import br.com.veck.util.Constants;
import br.com.veck.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerApiTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
    public void test_http_methods() throws Exception {
                                              
        MvcResult result = this.mockMvc.perform(post(Constants.CUSTOMER_SERVICE_ENDPOINT)        		
            	.content(Util.convertObjectToJson(new Customer()))
    			.contentType(MediaType.APPLICATION_JSON_UTF8)			
    			.accept(MediaType.APPLICATION_JSON_UTF8))        	
        		.andDo(print())	
        		.andExpect(status().isOk())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            	.andExpect(handler().handlerType(CustomerController.class))
            	.andExpect(handler().methodName("create"))
            	.andReturn();
        
        Gson gson = new Gson();
        String json = result.getResponse().getContentAsString();
        Customer customerAdded = gson.fromJson(json, Customer.class);
        
        this.mockMvc.perform(get(Constants.CUSTOMER_SERVICE_ENDPOINT))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	    	.andExpect(handler().handlerType(CustomerController.class))
	    	.andExpect(handler().methodName("consultAll"));  
        
        this.mockMvc.perform(get(Constants.CUSTOMER_SERVICE_ENDPOINT + "/{id}", customerAdded.getId()))         	
	    	.andDo(print())
	    	.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	    	.andExpect(handler().handlerType(CustomerController.class))
	    	.andExpect(handler().methodName("consult"));
                        
        this.mockMvc.perform(put(Constants.CUSTOMER_SERVICE_ENDPOINT)  
        	.content(Util.convertObjectToJson(customerAdded))
			.contentType(MediaType.APPLICATION_JSON_UTF8)    			
			.accept(MediaType.APPLICATION_JSON))		
        	.andDo(print())
        	.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(handler().handlerType(CustomerController.class))
        	.andExpect(handler().methodName("update"));
        
        this.mockMvc.perform(delete(Constants.CUSTOMER_SERVICE_ENDPOINT + "/{id}", customerAdded.getId()))
        	.andExpect(status().isOk())
    		.andExpect(handler().handlerType(CustomerController.class))        	
    		.andExpect(handler().methodName("delete"));
    }
	
}
