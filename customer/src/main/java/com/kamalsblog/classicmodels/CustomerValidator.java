package com.kamalsblog.classicmodels;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomerValidator implements ConstraintValidator<ICustomerConstraint, Customer>{
	String classMessage;
	@Override
    public void initialize(ICustomerConstraint constraint) {
		classMessage = constraint.message();
    }
	
	@Override
	public boolean isValid(Customer value, ConstraintValidatorContext context) {
		boolean isValid = true;
		if (value.getCustomerNumber() < 100 ) {
			isValid = false;
			context
			.buildConstraintViolationWithTemplate(classMessage +
					"Customer Number cannot be < 100")
			.addPropertyNode("customerNumber")
			.addConstraintViolation()
			.disableDefaultConstraintViolation();
		}
		if (value.getPostalCode() == null || value.getPostalCode().trim().isEmpty() ) {
			isValid = false;
			context
			.buildConstraintViolationWithTemplate(classMessage +
					"Postal Code cannot be null")
			.addPropertyNode("postalCode")
			.addConstraintViolation()
			.disableDefaultConstraintViolation();
		}
		return isValid;
	}
	

}
