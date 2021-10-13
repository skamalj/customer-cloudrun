package com.kamalsblog.classicmodels;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kamalsblog.classicmodels.CustomerController.CustomerIDNotFoundException;
import com.kamalsblog.classicmodels.CustomerController.InvalidCustomerDetailsException;

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
	String CInvalidCustomerDetailsHandler(InvalidCustomerDetailsException ex) {
		return ex.getMessage();
	}

}
