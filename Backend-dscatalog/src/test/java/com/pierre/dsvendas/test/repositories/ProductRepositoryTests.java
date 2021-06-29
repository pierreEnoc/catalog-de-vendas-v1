package com.pierre.dsvendas.test.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.pierre.dsvendas.entities.Product;
import com.pierre.dsvendas.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Test
	public void deleteShouldDeleteObjectWhendExists() {
		
		productRepository.deleteById(1L);
		
		Optional<Product> result = productRepository.findById(1L);
		
		Assertions.assertFalse(result.isPresent());
		
	}
	
	

}
