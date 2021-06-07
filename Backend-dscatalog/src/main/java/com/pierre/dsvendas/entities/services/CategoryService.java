package com.pierre.dsvendas.entities.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pierre.dsvendas.dto.CategoryDTO;
import com.pierre.dsvendas.entities.Category;
import com.pierre.dsvendas.entities.services.exception.DatabaseException;
import com.pierre.dsvendas.entities.services.exception.ResourceFoundException;
import com.pierre.dsvendas.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
      Page<Category> list = categoryRepository.findAll(pageRequest);
      return list.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }
  
    @Transactional(readOnly = true)
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
      try {
          Category entity = categoryRepository.getOne(id);
          entity.setName(dto.getName());
          entity = categoryRepository.save(entity);
          return new CategoryDTO(entity);
      }catch (EntityNotFoundException e) {
       throw new ResourceFoundException("Id not found" + id);
      }
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
