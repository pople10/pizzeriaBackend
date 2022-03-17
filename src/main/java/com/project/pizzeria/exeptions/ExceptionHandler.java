package com.project.pizzeria.exeptions;

public class ExceptionHandler {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public ExceptionHandler(Exception e)
	{
		this.setMessage(e.getMessage());
	}
	
}
