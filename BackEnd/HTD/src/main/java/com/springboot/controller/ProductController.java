package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.model.Product;
import com.springboot.service.ProductService;

@CrossOrigin
@RestController
public class ProductController {

	@Autowired
	ProductService productService;
    //------------------add a new product to the db--------------------------------
	@PostMapping("/product")
	public String addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
    //--------------fetch all product from the db-----------------------------------

	@GetMapping("/product")
	public List<Product> fetchAllProducts(){
		return productService.fetchAllProducts();
	}
	
    //--------------fetch available products from the db------------------------------------
	@GetMapping("/product/available")
	public List<Product> fetchAvailableProducts(){
		return productService.fetchAvailableProducts();
	}
	
    //--------------update product based on id--------------------------------
	@PutMapping("product/{id}")
	public String updateProduct(@PathVariable("id") Long id,@RequestBody Product newProduct) {
		return productService.updateProduct(id, newProduct);
	}
}
