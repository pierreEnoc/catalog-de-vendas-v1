package com.pierre.dsvendas.test.factory;

import java.time.Instant;

import com.pierre.dsvendas.dto.ProductDTO;
import com.pierre.dsvendas.entities.Category;
import com.pierre.dsvendas.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good phone", 800.0, "https://img.com/img.png", Instant.parse("2021-08-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, null));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();

		return new ProductDTO(product, product.getCategories());
	}
	
	public static ProductDTO createProductDTO(Long id) {
		ProductDTO dto = createProductDTO();
		dto.setId(id);
		return dto;
	}

}
