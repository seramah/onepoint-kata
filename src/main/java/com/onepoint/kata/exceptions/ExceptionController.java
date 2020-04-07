package com.onepoint.kata.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author srh
 *
 */
@ControllerAdvice
@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(ProductDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleProductDoesNotExistException(Throwable ex) {
        return ex.getMessage();
    }
	
	@ExceptionHandler(OrderDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleOrderDoesNotExistException(Throwable ex) {
        return ex.getMessage();
    }
	
	
	@ExceptionHandler(OrderHasNoProductException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleOrderHasNoProductException(Throwable ex) {
        return ex.getMessage();
    }
	
	@ExceptionHandler(DeletePaidOrderException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleDeletePaidOrderException(Throwable ex) {
        return ex.getMessage();
    }
	
	@ExceptionHandler(SortProductParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleSortProductParameterException(Throwable ex) {
        return ex.getMessage();
    }
}
