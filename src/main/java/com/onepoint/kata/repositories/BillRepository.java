package com.onepoint.kata.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onepoint.kata.entities.Bill;

/**
 * @author srh
 *
 */
@Repository
public interface BillRepository extends CrudRepository<Bill, Long>{

}
