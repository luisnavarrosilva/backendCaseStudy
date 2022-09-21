package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.model.Product;
import com.springboot.model.ProductOrder;
import com.springboot.service.OrderService;

@CrossOrigin
@RestController
public class ProductOrderController {

	@Autowired
	private OrderService orderService;
	
	//-------------Add a product to an order------------------------------------
	@PostMapping("/order/product/{oid}/{pid}")
	public ProductOrder addProductToOrder(@RequestBody ProductOrder productOrder,
			@PathVariable("oid") Long oid,
			@PathVariable("pid") Long pid) { 
		return orderService.addProductToOrder(productOrder, oid, pid);
	}
	
	//------------fetch all products based on order id-----------------------------------
	@GetMapping("/order/product/{oid}")
	public List<Product> getProductsByOrderId(@PathVariable("oid") Long oid ){
		return orderService.getProductsByOrderId(oid);
	}
}
