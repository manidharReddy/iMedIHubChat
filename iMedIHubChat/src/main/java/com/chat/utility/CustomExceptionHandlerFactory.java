package com.chat.utility;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory factory;
	
	
	public CustomExceptionHandlerFactory(ExceptionHandlerFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		//System.out.println("getExceptionHandler()");
		ExceptionHandler handler = new CustomExceptionHandler(this.factory.getExceptionHandler());
		return handler;
	}

}
