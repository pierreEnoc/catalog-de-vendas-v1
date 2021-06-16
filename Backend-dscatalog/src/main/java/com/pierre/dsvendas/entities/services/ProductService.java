package com.pierre.dsvendas.entities.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pierre.dsvendas.dto.CategoryDTO;
import com.pierre.dsvendas.dto.ProductDTO;
import com.pierre.dsvendas.entities.Category;
import com.pierre.dsvendas.entities.Product;
import com.pierre.dsvendas.entities.services.exception.DatabaseException;
import com.pierre.dsvendas.entities.services.exception.ResourceFoundException;
import com.pierre.dsvendas.repositories.CategoryRepository;
import com.pierre.dsvendas.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly= true)
	public Page< ProductDTO> findAllPaged(Long categoryId, String name, PageRequest pageRequest) {
		List<Category> categories = (categoryId == 0) ? null : Arrays.asList(categoryRepository.getOne(categoryId));
		Page<Product> page = productRepository.find(categories, name, pageRequest);
		productRepository.findProductsWithCategories(page.getContent());
		return page.map(x -> new ProductDTO(x, x.getCategories()));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceFoundException("Entity not found"));

		return new ProductDTO(entity, entity.getCategories());
	}
		
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDToToEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = productRepository.getOne(id);
			copyDToToEntity(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceFoundException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			 throw new ResourceFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
	}
	
  }
	
	private void copyDToToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
		
	}

}
