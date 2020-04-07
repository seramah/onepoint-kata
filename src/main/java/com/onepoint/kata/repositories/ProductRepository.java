package com.onepoint.kata.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onepoint.kata.entities.Product;

/**
 * @author srh
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findAll();
}
