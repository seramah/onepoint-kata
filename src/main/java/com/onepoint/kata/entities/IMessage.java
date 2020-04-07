package com.onepoint.kata.entities;

/**
 * @author srh
 *
 */
public interface IMessage {

	public static final String 				ERROR_ORDER_NOT_EXIST 							= "error_order_not_exist";
	public static final String 				ERROR_ORDER_MUST_HAVE_PRODUCT 					= "error_order_must_have_product";
	public static final String 				ERROR_CANT_DELETE_PAID_ORDER 					= "error_cant_delete_paid_order";
	
	public static final String 				ERROR_PRODUCT_NOT_EXIST 						= "error_product_not_exist";
	public static final String 				ERROR_PRODUCT_SORT_ATTRIBUT						= "error_product_sort_attribut";
}
