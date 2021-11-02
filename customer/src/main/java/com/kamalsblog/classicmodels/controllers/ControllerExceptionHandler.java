package com.kamalsblog.classicmodels.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kamalsblog.classicmodels.controllers.CustomerController.CustomerCreateOrUpdateException;
import com.kamalsblog.classicmodels.controllers.CustomerController.CustomerIDNotFoundException;
import com.kamalsblog.classicmodels.controllers.CustomerController.InvalidCustomerDetailsException;
import com.kamalsblog.classicmodels.controllers.OrderController.InvalidOrderDetailsException;
import com.kamalsblog.classicmodels.controllers.OrderController.OrderCreateOrUpdateException;
import com.kamalsblog.classicmodels.controllers.OrderController.OrderIDNotFoundException;
import com.kamalsblog.classicmodels.controllers.OrderController.OrderNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value=CustomerIDNotFoundException.class)
	String CustomerIDNotFoundHandler(CustomerIDNotFoundException ex) {
		return ex.getMessage();
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value=InvalidCustomerDetailsException.class)
	String InvalidCustomerDetailsHandler(InvalidCustomerDetailsException ex) {
		return ex.getMessage();
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value=CustomerCreateOrUpdateException.class)
	String CustomerCreateOrUpdateHandler(CustomerCreateOrUpdateException ex) {
		return ex.getMessage();
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value=OrderNotFoundException.class)
	String OrderNotFoundHandler(OrderNotFoundException ex) {
		return ex.getMessage();
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value=OrderCreateOrUpdateException.class)
	String OrderCreateORUpdateHandler(OrderCreateOrUpdateException ex) {
		return ex.getMessage();
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value=InvalidOrderDetailsException.class)
	String InvalidOrderDetailsHandler(InvalidOrderDetailsException ex) {
		return ex.getMessage();
	}
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value=OrderIDNotFoundException.class)
	String OrderIDNotFoundHandler(OrderIDNotFoundException ex) {
		return ex.getMessage();
	}
}
