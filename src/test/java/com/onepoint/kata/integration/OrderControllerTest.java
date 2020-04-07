package com.onepoint.kata.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepoint.kata.entities.IMessage;
import com.onepoint.kata.entities.Order;
import com.onepoint.kata.entities.OrderStatus;
import com.onepoint.kata.entities.Product;
import com.onepoint.kata.repositories.OrderRepository;
import com.onepoint.kata.services.MessageByLocaleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest implements IMessage{

	private final int product1Id = 1;
	private final int product2Id = 2;
	private final int product3Id = 3;
	private final int product4Id = 3;
	
	private final String product1Name = "Product M";
	private final String product2Name = "Product B";
	private final String product3Name = "Product A";
	private final String product4Name = "Product Z";
	
	private final double product1Price = 14.99;
	private final double product2Price = 99.99;
	private final double product3Price = 49.99;
	private final double product4Price = 2049.99;
	
	private final int product1Weight = 20;
	private final int product2Weight = 5;
	private final int product3Weight = 1000;
	private final int product4Weight = 50;
	
	
	private final  long order1Id = 1;
	private final  long order2Id = 2;
	private final  long order3Id = 3;
	private final  long order4Id = 4;
	
	String order1Body;
	String order2Body;
	String order3Body;
	String order4Body;

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository mockRepository;
    
    @Autowired
	MessageByLocaleService messageService;
	
	@Before
    public void init() throws JsonProcessingException {
    	Product p1 = new Product();
        p1.setId(product1Id);
        p1.setName(product1Name);
        p1.setPrice(product1Price);
        p1.setWeight(product1Weight);
        
        Product p2 = new Product();
        p2.setId(product2Id);
        p2.setName(product2Name);
        p2.setPrice(product2Price);
        p2.setWeight(product2Weight);
        
        Product p3 = new Product();
        p3.setId(product3Id);
        p3.setName(product3Name);
        p3.setPrice(product3Price);
        p3.setWeight(product3Weight);
        
        Product p4 = new Product();
        p4.setId(product4Id);
        p4.setName(product4Name);
        p4.setPrice(product4Price);
        p4.setWeight(product4Weight);
        
        List<Order> orders = new ArrayList<>();
        
        // commande numéro 1
        Order o1 = new Order();
        o1.setId(order1Id);
        o1.setStatus(OrderStatus.PENDING);
        List<Product> o1products =new ArrayList<>();
        o1products.add(p1);
        o1products.add(p2);
        o1products.add(p3);
        o1.setProducts(o1products);
        order1Body = new ObjectMapper().writeValueAsString(o1);
        
        // commande numéro 2
        Order o2 = new Order();
        o2.setId(order2Id);
        o2.setStatus(OrderStatus.PAID);
        List<Product> o2products =new ArrayList<>();
        o2products.add(p2);
        o2products.add(p3);
        o2.setProducts(o2products);
        order2Body = new ObjectMapper().writeValueAsString(o2);
        
        // commande numéro 3
        Order o3 = new Order();
        o3.setId(order3Id);
        o3.setStatus(OrderStatus.PENDING);
        order3Body = new ObjectMapper().writeValueAsString(o3);
        
        
        // commande numéro 4 
        Order o4 = new Order();
        o4.setId(order4Id);
        o4.setStatus(OrderStatus.PAID);
        List<Product> o4products =new ArrayList<>();
        o4products.add(p4);
        o4.setProducts(o4products);
        order4Body = new ObjectMapper().writeValueAsString(o4);
        
        
        // Liste des commandes
        orders.add(o1);
        orders.add(o2);
        
        when(mockRepository.findAll()).thenReturn(orders);
        
        when(mockRepository.findById(order1Id)).thenReturn(Optional.of(o1));
        when(mockRepository.findById(order2Id)).thenReturn(Optional.of(o2));
        when(mockRepository.findById(order3Id)).thenReturn(Optional.of(o3));
        
        when(mockRepository.save(o1)).thenReturn(o1);
        when(mockRepository.save(o2)).thenReturn(o2); 
        when(mockRepository.save(o4)).thenReturn(o4); 
       
    }
	
    
	/**
     * Teste la recherche d'une commande par id
     * @throws Exception
     */
    @Test
    public void getOrderByIdAndCheckWeightAndPriceTest() throws Exception {
    	int totalWeight = product1Weight + product2Weight+ product3Weight;
    	int shipmentAmount = (totalWeight % 10) * 25;;
    	double totalPrice = product1Price + product2Price + product3Price ;
        mockMvc.perform(get("/api/orders/{id}", order1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order1Id))
                .andExpect(jsonPath("$.weight").value(totalWeight))
                .andExpect(jsonPath("$.shipmentAmount").value(shipmentAmount))
                .andExpect(jsonPath("$.totalAmount").value(totalPrice));

        verify(mockRepository, times(1)).findById(order1Id);

    }
    
    /**
     * Teste la recherche d'une commande non existante
     * @throws Exception
     */
    @Test
    public void getOrderByIdNotFoundTest() throws Exception {
    	long id = 999;
        mockMvc.perform(get("/api/orders/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_ORDER_NOT_EXIST, id)));

        verify(mockRepository, times(1)).findById(id);

    }
    
    /**
     * Teste la liste des commandes
     * @throws Exception
     */
    @Test
    public void getAllOrdersTest() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order1Id))
                .andExpect(jsonPath("$[1].id").value(order2Id))
                .andExpect(jsonPath("$[0].products[0].id").value(product1Id))
                .andExpect(jsonPath("$[1].products[0].id").value(product2Id))
                .andExpect(jsonPath("$[1].products[1].id").value(product3Id));

        verify(mockRepository, times(1)).findAll();

    }
    
    
    /**
     * Teste l'ajout d'une commande
     * @throws Exception
     */
    @Test
    public void addOrderWithProductsTest() throws Exception {
        mockMvc.perform(post("/api/orders").content(order1Body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order1Id));

        verify(mockRepository, times(1)).save(any(Order.class));

    }
    
    
    /**
     * Teste l'ajout d'une commande
     * @throws Exception
     */
    @Test
    public void addOrderHasNoProductsTest() throws Exception {
        mockMvc.perform(post("/api/orders").content(order3Body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_ORDER_MUST_HAVE_PRODUCT, order3Id)));

        verify(mockRepository, times(0)).save(any(Order.class));
    }
    
    
    /**
     * Teste generation de facture
     * @throws Exception
     */
    @Test
    public void generateBillTest() throws Exception {
    	int totalWeight = + product2Weight+ product3Weight;
    	int shipmentAmount = (totalWeight % 10) * 25;;
    	double totalPrice =  product2Price + product3Price ;
        mockMvc.perform(post("/api/orders").content(order2Body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order2Id))
                .andExpect(jsonPath("$.totalAmount").value(totalPrice))
                .andExpect(jsonPath("$.bill.amount").value(totalPrice))
                .andExpect(jsonPath("$.bill.shipmentAmount").value(shipmentAmount));

        verify(mockRepository, times(1)).save(any(Order.class));

    }
    
    
    /**
     * Teste generation de facture
     * @throws Exception
     */
    @Test
    public void generateBillMinusFivePercentTest() throws Exception {
    	double totalPrice =  product4Price ;
    	double billPrice = BigDecimal.valueOf((totalPrice * 95) / 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        mockMvc.perform(post("/api/orders").content(order4Body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order4Id))
                .andExpect(jsonPath("$.totalAmount").value(totalPrice))
                .andExpect(jsonPath("$.bill.amount").value(billPrice));

        verify(mockRepository, times(1)).save(any(Order.class));

    }
    
    
    /**
     * Teste la suppression d'une commande
     * @throws Exception
     */
    @Test
    public void removeExistingOrderTest() throws Exception {
        mockMvc.perform(delete("/api/orders/{id}", order1Id))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).findById(order1Id);
        verify(mockRepository, times(1)).delete(any(Order.class));

    }
    
    /**
     * Teste la suppression d'une commande non existante
     * @throws Exception
     */
    @Test
    public void removeNotExistingOrderTest() throws Exception {
    	long id = 999;
        mockMvc.perform(delete("/api/orders/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_ORDER_NOT_EXIST, id)));
        
        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(0)).deleteById(id);

    }
    
    
    /**
     * Teste la suppression d'une commande payée
     * @throws Exception
     */
    @Test
    public void removePaidOrderTest() throws Exception {
    	 mockMvc.perform(delete("/api/orders/{id}", order2Id))
         .andExpect(status().isInternalServerError())
         .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_CANT_DELETE_PAID_ORDER)));
 
		 verify(mockRepository, times(1)).findById(order2Id);
		 verify(mockRepository, times(0)).deleteById(order2Id);

    }
   
}
