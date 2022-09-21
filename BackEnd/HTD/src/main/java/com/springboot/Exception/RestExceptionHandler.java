package com.springboot.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<String> handleNotFoundException(NotFoundException notFoundException){
	
		return new ResponseEntity<>(notFoundException.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {DuplicatedElementException.class})
	public ResponseEntity<String> handleDuplicatedElementException(
			DuplicatedElementException duplicatedElementException){
	
		return new ResponseEntity<>(duplicatedElementException.getMessage(),HttpStatus.BAD_REQUEST);
	}

}
