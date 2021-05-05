package com.pierre.dsvendas.entities.services;

import com.pierre.dsvendas.dto.CategoryDTO;
import com.pierre.dsvendas.entities.Category;
import com.pierre.dsvendas.entities.services.exception.ResourceFoundException;
import com.pierre.dsvendas.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
      List<Category> list = categoryRepository.findAll();
      return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
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

    @Transactional(readOnly = true)
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

}
