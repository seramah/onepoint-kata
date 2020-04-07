package com.onepoint.kata.integration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onepoint.kata.entities.Bill;
import com.onepoint.kata.entities.IMessage;
import com.onepoint.kata.repositories.BillRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BillControllerTest implements IMessage{


	private final long bill1Id = 1;
	private final long bill2Id = 2;
	private final long bill3Id = 2;
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String bill1Date = "07/06/2019";
    private String bill2Date = "07/06/2013";
    private String bill3Date = "07/06/2017";
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillRepository mockRepository;
	

    
	@Before
    public void init() throws JsonProcessingException, ParseException {
		List<Bill> bills = new ArrayList<Bill>();
    	Bill b1 =new Bill();
    	b1.setId(bill1Id);
    	b1.setCreationDate(formatter.parse(bill1Date));
        
    	Bill b2 =new Bill();
    	b2.setId(bill2Id);
    	b2.setCreationDate(formatter.parse(bill2Date));
    	
    	Bill b3 =new Bill();
    	b3.setId(bill3Id);
    	b3.setCreationDate(formatter.parse(bill3Date));
        bills.add(b1);
        bills.add(b2);
        bills.add(b3);
        
        when(mockRepository.findAll()).thenReturn(bills);
        
        List<Bill> sortedBills = bills.stream().sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate())).collect(Collectors.toList());
        when(mockRepository.findAll(Sort.by(Sort.Direction.ASC, "creationDate"))).thenReturn(sortedBills);
        
        
        
    }
	
	
    
	/**
     * Teste la liste des factures
     * @throws Exception
     */
    @Test
    public void getBillsTest() throws Exception {
        mockMvc.perform(get("/api/bills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bill1Id))
                .andExpect(jsonPath("$[1].id").value(bill2Id))
                .andExpect(jsonPath("$[2].id").value(bill3Id));

        verify(mockRepository, times(1)).findAll();
    }
    
    
    /**
     * Teste la liste des factures triées par date
     * @throws Exception
     */
    @Test
    public void getSortedBillsTest() throws Exception {
        mockMvc.perform(get("/api/bills/sorted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bill2Id))
                .andExpect(jsonPath("$[1].id").value(bill3Id))
                .andExpect(jsonPath("$[2].id").value(bill1Id));

        verify(mockRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "creationDate"));
    }
    

  
}
