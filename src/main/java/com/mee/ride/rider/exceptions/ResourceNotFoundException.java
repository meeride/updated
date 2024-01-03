package com.mee.ride.rider.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String status;
	String message;
	public ResourceNotFoundException(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
	
}
