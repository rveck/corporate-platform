package br.com.veck.geolocalizationservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import br.com.veck.geolocalizationservice.controller.GeolocalizationController;
import br.com.veck.geolocalizationservice.util.Constants;
import br.com.veck.model.Geolocalization;
import br.com.veck.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GeolocalizationApiTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
    public void test_http_methods() throws Exception {
                                              
        MvcResult createResult = this.mockMvc.perform(post(Constants.GEOLOCALIZATION_ENDPOINT)        		
            	.content(Util.convertObjectToString(new Geolocalization()))
    			.contentType(MediaType.APPLICATION_JSON_UTF8)			
    			.accept(MediaType.APPLICATION_JSON_UTF8))        	
        		.andDo(print())	
            	.andExpect(handler().handlerType(GeolocalizationController.class))
            	.andExpect(handler().methodName("create"))
            	.andReturn();
        
        String json = createResult.getResponse().getContentAsString();
        Geolocalization geolocalization = Util.convertStringToObject(json, Geolocalization.class);
                        
        MvcResult consultResult = this.mockMvc.perform(get(Constants.GEOLOCALIZATION_ENDPOINT))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	    	.andExpect(handler().handlerType(GeolocalizationController.class))
	    	.andExpect(handler().methodName("consultAll"))
	    	.andReturn();
        
        String jsonConsult = consultResult.getResponse().getContentAsString();
        
        assertThat(jsonConsult).contains(geolocalization.getId());
        
        this.mockMvc.perform(delete(Constants.GEOLOCALIZATION_ENDPOINT + "/{id}", geolocalization.getId()))
	    	.andExpect(status().isOk())
			.andExpect(handler().handlerType(GeolocalizationController.class))        	
			.andExpect(handler().methodName("delete"));
    }
	
}
