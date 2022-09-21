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
import com.springboot.model.Orders;
import com.springboot.service.OrderService;

@CrossOrigin
@RestController
public class OrdersController {

	@Autowired
	private OrderService orderService;
	
    //-------------Add a single order to the db------------------------------------------
	@PostMapping("/orders/{uid}")
	public String addSingleOrder(@RequestBody Orders order, @PathVariable("uid") Long id) {
		return orderService.addSingleOrder(order, id);

	} 
	
    //-------------fetch all orders from db--------------------------------------
	@GetMapping("/orders")
	public List<Orders> fetchAllOrders(){
		return orderService.fetchAllOrders();
	}
	
	 //------------fetch all orders based on user id--------------------------------------------
	@GetMapping("/orders/user/{uid}")
	public List<Orders> fetchOrdersByUserId(@PathVariable("uid") Long uid){
		return orderService.fetchOrdersByUserId(uid);
	}
    //------------show all the orders based on status------------------------------------
	@GetMapping("/orders/{status}")
	public List<Orders> fetchOrdersByStatus(@PathVariable("status") String status){	
		return orderService.fetchOrdersByStatus(status);
	} 
    //------------complete an order based on id----------------------------------
	@PutMapping("/orders/complete/{oid}") 
	public String completOrder(@PathVariable("oid") Long oid) {
		return orderService.completOrder(oid);

			
	}
    //---------Cancel an order based on id---------------------------------------
	@PutMapping("/orders/cancel/{oid}") 
	public String cancelOrder(@PathVariable("oid") Long oid) {
		return orderService.cancelOrder(oid);

	}

}
