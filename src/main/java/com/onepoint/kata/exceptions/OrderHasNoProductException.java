package com.onepoint.kata.exceptions;

/**
 * @author srh
 *
 */
public class OrderHasNoProductException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderHasNoProductException(String message) {
		super(message);
	}

}
