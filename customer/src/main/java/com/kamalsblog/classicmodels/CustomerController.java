package com.kamalsblog.classicmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerRepository repository;

	@Autowired
	public CustomerController(MongoTemplate template, CustomerRepository repository) {
		this.repository = repository;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Customer> getCustomers(@RequestParam(name = "start", required = false, defaultValue = "0") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "15") int limit) {
		Page<Customer> page = repository.findAll(PageRequest.of(start, limit));
		return page.toList();

	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	Customer getCustomer(@PathVariable int id) {
		Optional<Customer> cust = repository.findById(id);
		if (cust.isPresent())
			return cust.get();
		else 
			throw new CustomerIDNotFoundException(id);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	Customer addCustomer(@Valid @RequestBody Customer c, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			List<String> errorMessages = new ArrayList();
			String errMessage = "Message: %s, RejectedValue: %s";
			for (FieldError err : bindingResult.getFieldErrors()) {
				errorMessages.add(String.format(errMessage, err.getDefaultMessage(), err.getRejectedValue()));
			}
			throw new InvalidCustomerDetailsException(String.join(",",errorMessages));
		}
		return null;
		//return repository.insert(c);
	}
	
	@SuppressWarnings("serial")
	public class CustomerIDNotFoundException extends RuntimeException {
		CustomerIDNotFoundException(int id){
			super("Customer ID not found: "+id);
		}
	}
	@SuppressWarnings("serial")
	public class InvalidCustomerDetailsException extends RuntimeException {
		InvalidCustomerDetailsException(String message){
			super(message);
		}
	}
}
