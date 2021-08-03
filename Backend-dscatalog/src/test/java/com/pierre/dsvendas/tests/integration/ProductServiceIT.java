package com.pierre.dsvendas.tests.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.workmailmessageflow.model.ResourceNotFoundException;
import com.pierre.dsvendas.dto.ProductDTO;
import com.pierre.dsvendas.entities.services.ProductService;
import com.pierre.dsvendas.repositories.ProductRepository;

@SpringBootTest
@Transactional
public class ProductServiceIT {
	

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProduct;
    private long countPCGamerProducts;
    private PageRequest pageRequest;
    
    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProduct = 25;
        countPCGamerProducts = 21L;
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class,() -> {
            service.delete(nonExistingId);
        });
    }
    
    @Test
    public void findAllPagedShouldReturnNothingWhenNameDoesNotExist() {
    	String name = "Camera";
    	
    	Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
    	Assertions.assertTrue(result.isEmpty());
    }
    
    
    @Test
    public void findAllPagedShouldReturnAllProductsWhenNameIsEmpty() {
    	String name = "";
    	
    	Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
    	Assertions.assertFalse(result.isEmpty());
    	Assertions.assertEquals(countTotalProduct, result.getTotalElements());
    }
    
    @Test
    public void findAllPagedShouldReturnProductsWhenNameExistsIgnoringCase() {
    	String name = "pc gAMeR";
    	
    	Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
    	Assertions.assertFalse(result.isEmpty());
    	Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
    }
    
 
}
