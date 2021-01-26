package com.stacksimplify.restservices.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}

	

	
}
