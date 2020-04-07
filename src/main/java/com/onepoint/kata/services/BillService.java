package com.onepoint.kata.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.onepoint.kata.entities.Bill;
import com.onepoint.kata.entities.IMessage;
import com.onepoint.kata.repositories.BillRepository;

/**
 * @author srh
 *
 */
@Service
public class BillService implements IMessage{
	@Autowired
	BillRepository billRep;


    /**
     * @return
     */
    public List<Bill> getBills() {
        return billRep.findAll();
    }
	
	

    /**
     * @return
     */
    public List<Bill> getSortedBills()  {
        return billRep.findAll(Sort.by(Sort.Direction.ASC, "creationDate"));
    }
	
	/**
	 * @param bill
	 * @return
	 */
    public Bill addBill(Bill bill)  {
		return billRep.save(bill);
	}
	
    

}
