package com.onepoint.kata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onepoint.kata.entities.Order;
import com.onepoint.kata.exceptions.DeletePaidOrderException;
import com.onepoint.kata.exceptions.OrderDoesNotExistException;
import com.onepoint.kata.exceptions.OrderHasNoProductException;
import com.onepoint.kata.services.OrderService;

/**
 * @author srh
 *
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	
	/**
	 * @param id
	 * @return
	 * @throws OrderDoesNotExistException
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getProduct(@PathVariable long id) throws OrderDoesNotExistException{
		return orderService.getOrder(id);
	}
	
	
	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
	
	/**
	 * @param product
	 * @return
	 * @throws OrderHasNoProductException 
	 */
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order addOrder(@RequestBody Order order) throws OrderHasNoProductException  {
		return orderService.addOrder(order);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws DeletePaidOrderException
	 * @throws OrderDoesNotExistException 
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeOrder(@PathVariable long id) throws DeletePaidOrderException, OrderDoesNotExistException{
		return orderService.removeOrder(id);
	}
	
}
