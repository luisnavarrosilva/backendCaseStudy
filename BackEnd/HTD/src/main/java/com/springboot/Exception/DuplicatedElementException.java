package com.springboot.Exception;

public class DuplicatedElementException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedElementException(String message) {
		super(message);
	}
}
