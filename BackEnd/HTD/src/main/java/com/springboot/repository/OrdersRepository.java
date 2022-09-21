package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	@Query("select o from Orders o where o.status=?1")
	List<Orders> getByStatus(String status);

	@Query("select o from Orders o Join o.user u where u.id=?1")
	List<Orders> getByUserId(Long uid);

	

}
