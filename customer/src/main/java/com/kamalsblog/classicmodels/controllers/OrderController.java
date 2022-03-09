package com.kamalsblog.classicmodels.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kamalsblog.classicmodels.models.OrderRepository;
import com.kamalsblog.classicmodels.models.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/order")
@Tag(name = "order", description = "Order REST APIs")
public class OrderController {

	private final OrderRepository repository;
	private final MongoTemplate template;

	@Autowired
	public OrderController(MongoTemplate template, OrderRepository repository) {
		this.repository = repository;
		this.template = template;
	}

	@Operation(summary = "Get all orders for a customer")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Order> getOrders(
			@Parameter(description = "Customer ID") @RequestParam(name = "cust_num", required = true) int cust_num) {

		Query query = new Query();
		query.addCriteria(Criteria.where("customerNumber").is(cust_num));
		List<Order> orders = template.find(query, Order.class);
		if (orders.isEmpty()) {
			throw new OrderNotFoundException(cust_num);
		}
		return orders;
	}

	@Operation(summary = "Upsert order - Update if exists else create")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	Order updateOrAddOrder(@Parameter(description = "Order object") @Valid @RequestBody Order o,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = new ArrayList<String>();
			String errMessage = "Message: %s, RejectedValue: %s";
			for (FieldError err : bindingResult.getFieldErrors()) {
				errorMessages.add(String.format(errMessage, err.getDefaultMessage(), err.getRejectedValue()));
			}
			throw new InvalidOrderDetailsException(String.join("\n", errorMessages));
		} else {
			try {
				return repository.save(o);
			} catch (Exception e) {
				throw new OrderCreateOrUpdateException("Update Order: " + e.getMessage());
			}
		}
	}
	@Operation(summary = "Delete Order by ID")
	@DeleteMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	void deleteCustomer(@Parameter(description = "Order ID") @PathVariable int id) {
		Optional<Order> order = repository.findById(id);
		if (order.isPresent())
			repository.delete(order.get());
		else 
			throw new OrderIDNotFoundException(id);
	}

	@SuppressWarnings("serial")
	public class OrderNotFoundException extends RuntimeException {
		OrderNotFoundException(int id) {
			super("No orders found for customer: " + id);
		}
	}
	
	@SuppressWarnings("serial")
	public class OrderIDNotFoundException extends RuntimeException {
		OrderIDNotFoundException(int id) {
			super("Order ID not found: " + id);
		}
	}

	@SuppressWarnings("serial")
	public class OrderCreateOrUpdateException extends RuntimeException {
		OrderCreateOrUpdateException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	public class InvalidOrderDetailsException extends RuntimeException {
		InvalidOrderDetailsException(String message) {
			super(message);
		}
	}

}
