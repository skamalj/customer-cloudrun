package com.kamalsblog.classicmodels.models;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OrderValidator implements ConstraintValidator<IOrderConstraint, Order> {

	@Autowired
	RestTemplate resttemplate;

	String classMessage;

	@Override
	public void initialize(IOrderConstraint constraint) {
		classMessage = constraint.message();
	}

	@Override
	public boolean isValid(Order value, ConstraintValidatorContext context) {
		boolean isValid = true;
		if (!(value.getRequiredDate() != null && value.getRequiredDate().after(value.getOrderDate()))) {
			isValid = false;
			context.buildConstraintViolationWithTemplate(
					classMessage + "Required date cannot be null or before Order date").addPropertyNode("RequiredDate")
					.addConstraintViolation().disableDefaultConstraintViolation();
		}

		ResponseEntity<String> res = resttemplate.getForEntity("http://localhost:8080/customer/{id}", String.class,
				value.getCustomerNumber());
		if (res.getStatusCode() != HttpStatus.OK) {
			isValid = false;
			context.buildConstraintViolationWithTemplate(classMessage + "Invalid Customer Number")
					.addPropertyNode("CustomerNumber").addConstraintViolation().disableDefaultConstraintViolation();
		}

		return isValid;
	}

}
