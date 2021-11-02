package com.kamalsblog.classicmodels.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Aspect
@Component
class LoggingAspect {
	  
	private static final Logger logger  = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.kamalsblog.classicmodels..*)")
    public void logMethod() {}
    
    @Before("logMethod()")
    public void logBeforeMethodCall(JoinPoint jp) throws Throwable{
        logger.info("Before Method: " + jp.toString() +  " AOP was called");
    }
    
    @AfterReturning(pointcut = "logMethod()", returning = "retval")
    public void logAfterMethodCall(JoinPoint jp, Object retval) {
    	logger.info("After Method: Return value is " + retval);
    }
    
}