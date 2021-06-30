package com.pierre.dsvendas.test.factory;

import java.time.Instant;

import com.pierre.dsvendas.dto.ProductDTO;
import com.pierre.dsvendas.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		return new Product(1L, "Phone", "Good phone", 800.0, "https://img.com/img.png", Instant.parse("2021-10-20T03:00:00Z"));
	}
	
	public static ProductDTO createProductDTO() {
		return new ProductDTO(createProduct());
	}

}
