package com.onepoint.kata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onepoint.kata.entities.Bill;


/**
 * @author srh
 *
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{

}
