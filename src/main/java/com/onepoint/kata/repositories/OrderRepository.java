package com.onepoint.kata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onepoint.kata.entities.Order;

/**
 * @author srh
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
