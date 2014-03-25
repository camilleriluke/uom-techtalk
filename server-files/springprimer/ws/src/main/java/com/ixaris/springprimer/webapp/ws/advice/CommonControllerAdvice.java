/*
 * Copyright 2002, 2013 Ixaris Systems Ltd.  All rights reserved.                    
 * IXARIS PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */
package com.ixaris.springprimer.webapp.ws.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ixaris.springprimer.webapp.ws.validation.ValidationModel;


/**
 * 
 * The base controller for all the OPS console REST controllers. Basically it handles the exceptions thrown by the controllers.
 * 
 * @author jan.gatt
 */
@ControllerAdvice
public class CommonControllerAdvice {
    
    private static final Logger LOG = LoggerFactory.getLogger(CommonControllerAdvice.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationModel validationHandler(final MethodArgumentNotValidException e) {
        
        return ValidationModel.prepare(e.getBindingResult());
    }
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(final RuntimeException e) {
        
        LOG.error("Error", e);
        return e.getMessage();
    }
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
	public String handleException(final Exception e) {
		
        LOG.error("Error", e);
        return e.getMessage();
	}
    
}
