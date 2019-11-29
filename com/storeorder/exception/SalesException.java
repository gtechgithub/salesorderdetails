package com.storeorder.exception;

public class SalesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String message;

	public SalesException(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
