package com.springboot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.springboot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("select p from Product p where p.status=?1")
	List<Product> getByStatus(String status);

	@Query("select p from Product p where p.name=?1")
	Product getByName(String name);
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.name=?1, "
			+ "p.description=?2, "
			+ "p.price=?3, "
			+ "p.status=?4, "
			+ "p.stock=?5, "
			+ "p.image=?6 "
			+ "where p.id=?7")
	void updateProduct(String name, 
			String description, 
			Double price, 
			String status, 
			int stock, 
			String image,
			Long id);
	
	@Query("select p from Product p where p.id=?1")
	Product findProductByid(Long id);

	@Query("select p from Product p where p.id=?1")
	Product getProductById(Long pid);


}
