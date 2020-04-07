package com.onepoint.kata.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onepoint.kata.entities.IMessage;
import com.onepoint.kata.entities.Order;
import com.onepoint.kata.entities.OrderStatus;
import com.onepoint.kata.exceptions.DeletePaidOrderException;
import com.onepoint.kata.exceptions.OrderDoesNotExistException;
import com.onepoint.kata.exceptions.OrderHasNoProductException;
import com.onepoint.kata.exceptions.ProductDoesNotExistException;
import com.onepoint.kata.repositories.OrderRepository;

/**
 * @author srh
 *
 */
@Service
public class OrderService implements IMessage{
	
	@Autowired
	OrderRepository orderRep;
	
	@Autowired
	MessageByLocaleService messageService;
	
	/**
     * @return
     * @throws ProductDoesNotExistException 
     */
    public Order getOrder(long id) throws OrderDoesNotExistException {
    	Optional<Order> order = orderRep.findById(id);
		if (!order.isPresent()) {
			throw new OrderDoesNotExistException(messageService.getMessage(ERROR_ORDER_NOT_EXIST, id));
		}
		return order.get();
    }
	
	/**
	 * @return
	 */
    public List<Order> getOrders() {
        return orderRep.findAll();
    }
	
	/**
	 * @param product
	 * @return
	 * @throws OrderHasNoProductException 
	 */
    public Order addOrder(Order order) throws OrderHasNoProductException  {
    	if (order.getProducts()==null || order.getProducts().isEmpty()) {
    		throw new OrderHasNoProductException(messageService.getMessage(ERROR_ORDER_MUST_HAVE_PRODUCT, order.getId()));
    	}
		return orderRep.save(order);
	}
	

    /**
     * @param id
     * @return
     * @throws DeletePaidOrderException 
     * @throws OrderDoesNotExistException 
     */
    public boolean removeOrder(long id) throws DeletePaidOrderException, OrderDoesNotExistException{
		boolean result = false;
		Optional<Order> order = orderRep.findById(id);
		
		// On ne peut pas supprimer une commande payé
		if (order.isPresent()) {
			if (order.get().getStatus().equals(OrderStatus.PAID)) {
				throw new DeletePaidOrderException(messageService.getMessage(ERROR_CANT_DELETE_PAID_ORDER));
			}
			orderRep.delete(order.get());
			result = true;
		} else {
			throw new OrderDoesNotExistException(messageService.getMessage(ERROR_ORDER_NOT_EXIST, id));
		}
		return result;
	}
	
}
