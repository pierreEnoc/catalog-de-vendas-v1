package com.pierre.dsvendas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pierre.dsvendas.entities.Category;
import com.pierre.dsvendas.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
			+ "(:category IS NULL OR :category IN cats) AND "
			+ "(LOWER(obj.name) LIKE LOWER(CONCAT('%', :name,'%')) )")
	Page<Product> find(Category category, String name, Pageable pageable);

}
