package com.springboot.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.Exception.DuplicatedElementException;
import com.springboot.Exception.NotFoundException;
import com.springboot.model.Product;
import com.springboot.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
    //------------------add a new product to the db--------------------------------
	public String addProduct(Product product) {
		Product p=productRepository.getByName(product.getName());
		if(p!=null)
			throw new DuplicatedElementException("This product already exist");
		productRepository.save(product);
		return "Product added succesfully";
	}
	 
    //--------------fetch all product from the db-----------------------------------
	public List<Product> fetchAllProducts(){
		return productRepository.findAll();
	}
    //--------------fetch available products from the db------------------------------------
	public List<Product> fetchAvailableProducts(){
		return productRepository.getByStatus("available");
	}
	
    //--------------update product based on id--------------------------------
	public String updateProduct( Long id, Product newProduct) {
		Product p=productRepository.findProductByid(id);
		if(p==null)
			throw new NotFoundException("El producto con id "+id+
					" no se encuentra en la base de datos");
		productRepository.updateProduct(newProduct.getName(),newProduct.getDescription(),
				newProduct.getPrice(),newProduct.getStatus(),newProduct.getStock(),
				newProduct.getImage(),id);
		return "Product updated succesfully";
	}
}
