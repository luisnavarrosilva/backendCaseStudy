package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.model.Product;
import com.springboot.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{ 

	@Query("select p from ProductOrder po JOIN po.orders o JOIN po.product p where o.id=?1")
	List<Product> getProductById(Long oid);

}
