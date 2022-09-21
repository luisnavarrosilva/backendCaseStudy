package com.springboot.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.springboot.Exception.NotFoundException;
import com.springboot.model.Orders;
import com.springboot.model.Product;
import com.springboot.model.ProductOrder;
import com.springboot.model.UserInfo;
import com.springboot.repository.OrdersRepository;
import com.springboot.repository.ProductOrderRepository;
import com.springboot.repository.ProductRepository;
import com.springboot.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductOrderRepository productOrderRepository;
	
    //-------------Add a single order to the db------------------------------------------
	public String addSingleOrder(Orders order,Long id) {
		UserInfo user=userRepository.getReferenceById(id);
		if(user==null)
			throw new NotFoundException("El usuario con id "+id+
					" no se encuentra en la base de datos");		
		order.setUser(user);
		ordersRepository.save(order);
		return "Order created succesfully";
	} 
    //-------------fetch all orders from db--------------------------------------
	public List<Orders> fetchAllOrders(){
		return ordersRepository.findAll(); 
	}
	
	 //------------fetch all orders based on user id--------------------------------------------
	public List<Orders> fetchOrdersByUserId(Long uid){
		UserInfo user=userRepository.getReferenceById(uid);
		if(user==null)
			throw new NotFoundException("El usuario con id "+uid+
					" no se encuentra en la base de datos");
		return ordersRepository.getByUserId(uid);
	}
    //------------show all the orders based on status------------------------------------
	@GetMapping("/orders/{status}")
	public List<Orders> fetchOrdersByStatus(String status){
		return ordersRepository.getByStatus(status);
	} 
    //------------complete an order based on id----------------------------------
	public String completOrder(Long oid) {
		Orders order=ordersRepository.getReferenceById(oid);
		if(order==null)
			throw new NotFoundException("la orden con id "+oid+
					" no se encuentra en la base de datos");
		order.setStatus("completed");
		ordersRepository.save(order);
		UserInfo user=order.getUser();
		senderService.sendEmail(user.getEmail(), "New completed order", 
		"You have added a new order");
		return "Completed order succesfully";
			
	}
    //---------Cancel an order based on id---------------------------------------
	public String cancelOrder(Long oid) {
		Orders order=ordersRepository.getReferenceById(oid);
		if(order==null)
			throw new NotFoundException("la orden con id "+oid+
					" no se encuentra en la base de datos");
		order.setStatus("pending");
		ordersRepository.save(order);
		UserInfo user=order.getUser();
		senderService.sendEmail(user.getEmail(), "Order cancellation", 
		"You have canceled an order succesfully");
		return "Cancellation succesfully";
			
	}
	//-------------Add a product to an order------------------------------------
	public ProductOrder addProductToOrder(ProductOrder productOrder, Long oid, Long pid) { 
		//fetch order from db
		Orders order=ordersRepository.getReferenceById(oid);
		//fetch product from db
		Product product=productRepository.getReferenceById(pid);
		
		//set order and product to the db
		productOrder.setOrder(order);
		productOrder.setProduct(product);
		
		return productOrderRepository.save(productOrder);
	}
		
	//------------fetch all products based on order id-----------------------------------
	public List<Product> getProductsByOrderId(Long oid ){
		return productOrderRepository.getProductById(oid); 
	}
}
