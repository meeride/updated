package com.mee.ride.rider.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mee.ride.rider.responses.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/*
	 * @ExceptionHandler() public ResponseEntity<CommonResponse>
	 * resourceNotFoundExceptionHandler(){
	 * 
	 * }
	 */
}
