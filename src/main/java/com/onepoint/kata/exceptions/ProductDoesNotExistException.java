package com.onepoint.kata.exceptions;

/**
 * @author srh
 *
 */
public class ProductDoesNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductDoesNotExistException(String message) {
		super(message);
	}

}
