package com.kamalsblog.classicmodels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.kamalsblog.classicmodels.models.RestTemplateErrorHandler;

@SpringBootApplication
public class CustomerApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
	    RestTemplate resttemplate =  new RestTemplate();
	    resttemplate.setErrorHandler(new RestTemplateErrorHandler());
	    return resttemplate;
	}

}
