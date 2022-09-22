package com.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.model.Product;
import com.springboot.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductService productService;
	
	private Product product;
	@Captor
	private ArgumentCaptor<Product> productEntity;
	
	@BeforeEach
	void setUp() {
		
		product=new Product();
		product.setId(5L);
		product.setName("Pantene");
		product.setDescription("shampoo");
		product.setImage("shampoo.jpg");
		product.setPrice(52.0);
		product.setStatus("available");
		product.setStock(5);
		
	}	
    //------------------add a new product to the db--------------------------------
	@Test
	void addProduct(){
		//mock
		when(productRepository.save(any())).thenReturn(product);
		//Capture de user saved
		productService.addProduct(product);
		verify(productRepository).save(productEntity.capture());
		//assertion
		assertThat(productEntity.getValue().getName()).isEqualTo(product.getName());
		assertThat(productEntity.getValue().getDescription()).isEqualTo(product.getDescription());

	}
    //--------------fetch all product from the db-----------------------------------
	@Test
	void findAllProducts() {
		when(productRepository.findAll()).thenReturn(Arrays.asList(product));
		assertNotNull(productService.fetchAllProducts());
	}
    //--------------fetch available products from the db------------------------------------
	@Test
	void fetchAvailableProducts() {
		//mock
		when(productRepository.getByStatus("available")).thenReturn(Arrays.asList(product));
		//assertion
		assertNotNull(productService.fetchAvailableProducts());
		assertThat(productService.fetchAvailableProducts()).isEqualTo(Arrays.asList(product));
		
	}
    //--------------update product based on id--------------------------------
	@Test
	void updateProduct() {
		//mock
		when(productRepository.save(any())).thenReturn(product);
		//Capture de user saved
		productService.addProduct(product);

		Long id=product.getId();
		Product newProduct=product;
		newProduct.setName("dove");
		newProduct.setDescription("jabon");
		newProduct.setImage("jabon.jpg");
				
		productService.addProduct(newProduct);
		verify(productRepository,times(2)).save(newProduct);
		
	}

	
}
