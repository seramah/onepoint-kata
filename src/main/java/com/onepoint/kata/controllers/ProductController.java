package com.onepoint.kata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onepoint.kata.entities.Product;
import com.onepoint.kata.exceptions.ProductDoesNotExistException;
import com.onepoint.kata.exceptions.SortProductParameterException;
import com.onepoint.kata.services.ProductService;

/**
 * @author srh
 *
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductService productService;
	
	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProducts() {
        return productService.getProducts();
    }
	
	/**
	 * @param id
	 * @return
	 * @throws ProductDoesNotExistException
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable long id) throws ProductDoesNotExistException{
		return productService.getProduct(id);
	}
	
	/**
	 * @param sort
	 * @return
	 * @throws SortProductParameterException 
	 */
	@RequestMapping(value = "", params = { "sort"},  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getSortedProducts(@RequestParam("sort") String sort) throws SortProductParameterException {
        return productService.getSortedProducts(sort);
    }
	
	/**
	 * @param product
	 * @return
	 */
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product addProduct(@RequestBody Product product)  {
		return productService.addProduct(product);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws ProductDoesNotExistException
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean removeProduct(@PathVariable long id) throws ProductDoesNotExistException{
		return productService.removeProduct(id);
	}
	
}
