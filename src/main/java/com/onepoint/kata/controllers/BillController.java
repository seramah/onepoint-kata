package com.onepoint.kata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onepoint.kata.entities.Bill;
import com.onepoint.kata.exceptions.SortProductParameterException;
import com.onepoint.kata.services.BillService;

/**
 * @author srh
 *
 */
@RestController
@RequestMapping("/api/bills")
public class BillController {

	@Autowired
	BillService billService;
	
	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bill> getBills() {
        return billService.getBills();
    }

	
	/**
	 * @param sort
	 * @return
	 * @throws SortProductParameterException 
	 */
	@RequestMapping(value = "/sorted",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bill> getSortedBills()  {
        return billService.getSortedBills();
    }
	
	
}
