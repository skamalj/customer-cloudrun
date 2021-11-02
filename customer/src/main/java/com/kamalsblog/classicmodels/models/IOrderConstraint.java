package com.kamalsblog.classicmodels.models;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { OrderValidator.class })
@Documented
public @interface IOrderConstraint {

    String message() default "com.kamalsblog.classifmodels.Order: ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}