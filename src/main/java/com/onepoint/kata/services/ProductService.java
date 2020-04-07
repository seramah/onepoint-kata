package com.onepoint.kata.services;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.onepoint.kata.entities.IMessage;
import com.onepoint.kata.entities.Product;
import com.onepoint.kata.exceptions.ProductDoesNotExistException;
import com.onepoint.kata.exceptions.SortProductParameterException;
import com.onepoint.kata.repositories.ProductRepository;

/**
 * @author srh
 *
 */
@Service
public class ProductService implements IMessage{
	@Autowired
	ProductRepository productRep;
	
	@Autowired
	MessageByLocaleService messageService;

    /**
     * @return
     */
    public List<Product> getProducts() {
        return productRep.findAll();
    }
	
    /**
     * @return
     * @throws ProductDoesNotExistException 
     */
    public Product getProduct(long id) throws ProductDoesNotExistException {
    	Optional<Product> product = productRep.findById(id);
		if (!product.isPresent()) {
			throw new ProductDoesNotExistException(messageService.getMessage(ERROR_PRODUCT_NOT_EXIST, id));
		}
		return product.get();
    }
	
    
    /**
     * @param sort
     * @return
     * @throws SortProductParameterException 
     */
    public List<Product> getSortedProducts(String sort) throws SortProductParameterException {
    	if (!productHasAttribute(sort)) {
    		throw new SortProductParameterException(messageService.getMessage(ERROR_PRODUCT_SORT_ATTRIBUT, sort));
    	}
        return productRep.findAll(Sort.by(Sort.Direction.ASC, sort));
    }
	
	/**
	 * @param product
	 * @return
	 */
    public Product addProduct(Product product)  {
		return productRep.save(product);
	}
	
    
    /**
     * @param id
     * @return
     * @throws ProductDoesNotExistException 
     */
    public boolean removeProduct(long id) throws ProductDoesNotExistException{
		Optional<Product> product = productRep.findById(id);
		if (product.isPresent()) {
			productRep.delete(product.get());
		} else {
			throw new ProductDoesNotExistException(messageService.getMessage(ERROR_PRODUCT_NOT_EXIST, id));
		}
		return true;
	}
	
    
    /**
     * @param name
     * @return
     */
    private boolean productHasAttribute(String name) {
    	Field[] farray =  Product.class.getDeclaredFields();
    	List<Field> fields =  Arrays.asList(farray);
    	return fields.stream().anyMatch(f -> f.getName().equals(name));
    	
    }
}
