package br.com.veck.customerservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.veck.customerservice.controller.CustomerController;
import br.com.veck.customerservice.model.Customer;
import br.com.veck.customerservice.util.Constants;
import br.com.veck.customerservice.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerApiTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
    public void test_http_methods() throws Exception {
        this.mockMvc.perform(get(Constants.ENDPOINT_CUSTOMER))
	    	.andDo(print())	    	
	    	.andExpect(handler().handlerType(CustomerController.class))
	    	.andExpect(handler().methodName("consultAll"));
                        
        this.mockMvc.perform(get(Constants.ENDPOINT_CUSTOMER + "/{id}", new String("1")))         	
        	.andDo(print())			
        	.andExpect(handler().handlerType(CustomerController.class))
        	.andExpect(handler().methodName("consult"));
/*        
        this.mockMvc.perform(post(Constants.ENDPOINT_CUSTOMER)
            	.content(Util.asJsonString(new Customer()))
    			.contentType(MediaType.APPLICATION_JSON)			
    			.accept(MediaType.APPLICATION_JSON))			
            	.andExpect(handler().handlerType(CustomerController.class))
            	.andExpect(handler().methodName("create"));
        
        this.mockMvc.perform(delete(Constants.ENDPOINT_CUSTOMER + "/{id}", new String("1")))        	    			    			    				
        	.andExpect(handler().handlerType(CustomerController.class))
        	.andExpect(handler().methodName("delete"));
        
        this.mockMvc.perform(put(Constants.ENDPOINT_CUSTOMER, Util.asJsonString(new Customer()))        	
			.contentType(MediaType.APPLICATION_JSON)    			
			.accept(MediaType.APPLICATION_JSON))		
        	.andExpect(handler().handlerType(CustomerController.class))
        	.andExpect(handler().methodName("update"));
*/
    }
	
}
