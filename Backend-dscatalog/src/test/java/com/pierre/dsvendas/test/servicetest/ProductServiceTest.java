// service test with mockito

package com.pierre.dsvendas.test.servicetest;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.services.workmailmessageflow.model.ResourceNotFoundException;
import com.pierre.dsvendas.dto.ProductDTO;
import com.pierre.dsvendas.entities.Product;
import com.pierre.dsvendas.entities.services.ProductService;
import com.pierre.dsvendas.entities.services.exceptions.DatabaseException;
import com.pierre.dsvendas.repositories.ProductRepository;
import com.pierre.dsvendas.test.factory.ProductFactory;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Product product;
    private PageImpl<Product> page;
    
    private ProductDTO productDTO;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        product = ProductFactory.createProduct();
        page = new PageImpl<>(List.of(product));
        productDTO = ProductFactory.createProductDTO();
        
        //productDTO = ProductFactory.createProduct();

        Mockito.when(repository.find(ArgumentMatchers.any(), ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(page);
        
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.getOne(existingId)).thenReturn(product);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }


    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class,() -> {
            service.delete(nonExistingId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void  deleteShouldThrowDatabaseExceptionWhenIdDependentId() {
        Assertions.assertThrows(DatabaseException.class,() -> {
            service.delete(dependentId);
        });
        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void findAllPageShouldReturnPage() {
        Long categoryId = 0L;
        String name = "";
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(categoryId, name, pageRequest);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Mockito.verify(repository, Mockito.times(1)).find(null, name, pageRequest);
    }
    
    
    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
    	ProductDTO resul = service.findById(existingId);
    	Assertions.assertNotNull(resul);
    }
    
    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
    	Assertions.assertThrows(ResourceNotFoundException.class, () -> {
    		service.findById(nonExistingId);
    	});
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        ProductDTO dto = new ProductDTO();
        ProductDTO result = service.update(existingId, dto);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        ProductDTO dto = new ProductDTO();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, dto);
        });
    }


}
