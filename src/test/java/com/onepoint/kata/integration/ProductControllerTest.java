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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepoint.kata.entities.IMessage;
import com.onepoint.kata.entities.Product;
import com.onepoint.kata.repositories.ProductRepository;
import com.onepoint.kata.services.MessageByLocaleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest implements IMessage{

	String product1Body;
	private final long product1Id = 1;
	private final long product2Id = 2;
	private final long product3Id = 3;
	
	private final String product1Name = "Product M";
	private final String product2Name = "Product B";
	private final String product3Name = "Product A";
	
	private final double product1Price = 14.99;
	private final double product2Price = 99.99;
	private final double product3Price = 49.99;
	
	private final int product1Weight = 20;
	private final int product2Weight = 5;
	private final  int product3Weight = 1000;
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository mockRepository;
	
    @Autowired
    MessageByLocaleService messageService;

    
	@Before
    public void init() throws JsonProcessingException {
		List<Product> products = new ArrayList<Product>();
    	Product p1 = new Product();
        p1.setId(product1Id);
        p1.setName(product1Name);
        p1.setPrice(product1Price);
        p1.setWeight(product1Weight);
        
        product1Body= new ObjectMapper().writeValueAsString( p1);
        
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
        
        
        products.add(p1);
        products.add(p2);
        products.add(p3);
        
        
        when(mockRepository.findAll()).thenReturn(products);
        
        when(mockRepository.findById(product1Id)).thenReturn(Optional.of(p1));
        
        List<Product> sortedProductsByWeight = products.stream().sorted((o1, o2) -> o1.getWeight() - o2.getWeight()).collect(Collectors.toList());
        when(mockRepository.findAll(Sort.by(Sort.Direction.ASC, "weight"))).thenReturn(sortedProductsByWeight);
        
        List<Product> sortedProductsByName = products.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        when(mockRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(sortedProductsByName);
        
        List<Product> sortedProductsByPrice = products.stream().sorted((o1, o2) ->Double.compare(o1.getPrice(), o2.getPrice())).collect(Collectors.toList());
        when(mockRepository.findAll(Sort.by(Sort.Direction.ASC, "price"))).thenReturn(sortedProductsByPrice);
        
        
        when(mockRepository.save(any(Product.class))).thenReturn(p1);
        
    }
	
	
    
	/**
     * Teste la liste des produits
     * @throws Exception
     */
    @Test
    public void getProductByIdTest() throws Exception {
        mockMvc.perform(get("/api/products/{id}", product1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product1Id));

        verify(mockRepository, times(1)).findById(product1Id);

    }
    
    /**
     * Teste la liste des produits
     * @throws Exception
     */
    @Test
    public void getProductByIdNotFoundTest() throws Exception {
    	long id = 999;
        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_PRODUCT_NOT_EXIST, id)));

        verify(mockRepository, times(1)).findById(id);

    }
    
    /**
     * Teste la liste des produits
     * @throws Exception
     */
    @Test
    public void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product1Id))
                .andExpect(jsonPath("$[1].id").value(product2Id));

        verify(mockRepository, times(1)).findAll();

    }
    
    
    /**
     * Teste les tri des produits par poids
     * @throws Exception
     */
    @Test
    public void getAllProductsSortedByWeightTest() throws Exception {  
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("sort", "weight");
        mockMvc.perform(get("/api/products").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].weight").value(product2Weight))
                .andExpect(jsonPath("$[1].weight").value(product1Weight))
                .andExpect(jsonPath("$[2].weight").value(product3Weight));

        verify(mockRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "weight"));

    }
    
    
    /**
     * Teste le tri des produits par nom
     * @throws Exception
     */
    @Test
    public void getAllProductsSortedByNameTest() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("sort", "name");
        mockMvc.perform(get("/api/products").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(product3Name))
                .andExpect(jsonPath("$[1].name").value(product2Name))
                .andExpect(jsonPath("$[2].name").value(product1Name));

        verify(mockRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "name"));

    }
    
    
    /**
     * Teste le tri des produits par prix
     * @throws Exception
     */
    @Test
    public void getAllProductsSortedByPriceTest() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("sort", "price");
        mockMvc.perform(get("/api/products").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(product1Price))
                .andExpect(jsonPath("$[1].price").value(product3Price))
                .andExpect(jsonPath("$[2].price").value(product2Price));

        verify(mockRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "price"));

    }
    
    
    /**
     * Teste le tri des produits par un  faux attribut
     * @throws Exception
     */
    @Test
    public void getAllProductsWrongSortAttributeTest() throws Exception {
    	String sort = "wrongAttribute";
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("sort", sort);
        mockMvc.perform(get("/api/products").params(requestParams))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_PRODUCT_SORT_ATTRIBUT, sort)));

        verify(mockRepository, times(0)).findAll(Sort.by(Sort.Direction.ASC, sort));

    }
    
    
    /**
     * Teste l'ajout d'un produit
     * @throws Exception
     */
    @Test
    public void addProductTest() throws Exception {
        mockMvc.perform(post("/api/products").content(product1Body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product1Id));

        verify(mockRepository, times(1)).save(any(Product.class));

    }
    
    
    
    /**
     * Teste la suppression d'un produit
     * @throws Exception
     */
    @Test
    public void removeExistingProductTest() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", product1Id))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).findById(product1Id);
        verify(mockRepository, times(1)).delete(any(Product.class));

    }
    
    /**
     * Teste la suppression d'un produit non existant
     * @throws Exception
     */
    @Test
    public void removeNotExistingProductTest() throws Exception {
    	long id = 999;
        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(messageService.getMessage(ERROR_PRODUCT_NOT_EXIST, id)));
        
        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(0)).deleteById(id);

    }
  
}
