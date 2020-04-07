package com.onepoint.kata.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.onepoint.kata.entities.Product;
import com.onepoint.kata.repositories.BillRepository;
import com.onepoint.kata.repositories.OrderRepository;
import com.onepoint.kata.repositories.ProductRepository;

/**
 * @author srh
 *
 */
@Component
public class DataInitializer implements ApplicationRunner{

	ProductRepository productRep;
	OrderRepository orderRep;
	BillRepository billRep;
	
	@Autowired
    public DataInitializer(ProductRepository productRep, OrderRepository orderRep, BillRepository billRep) {
        this.productRep = productRep;
        this.orderRep = orderRep;
        this.billRep = billRep;
    }
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Random r = new Random();
		 
		
		List<Product> products  = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
			Product p =new Product();
			p.setName("Product " + i);
			p.setPrice(BigDecimal.valueOf(1 + r.nextDouble() * 9).setScale(2, RoundingMode.HALF_UP).doubleValue());
			p.setWeight(r.nextInt(1000));
			products.add(p);
		}
		productRep.saveAll(products);
	}

}
