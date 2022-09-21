package com.springboot.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.springboot.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>{

	@Query("select u from UserInfo u where u.userName=?1 or u.email=?1")
	UserInfo getByUsernameOrEmail(String user);
	
	@Query("select u from UserInfo u where u.userName=?1 or u.email=?2")
	UserInfo getByUsernameOrEmail(String userName, String email);

	@Transactional
	@Modifying
	@Query("UPDATE UserInfo u SET u.password=?1, u.phoneNumber=?2,u.adress=?3 "
			+ "where u.id=?4")
	void updateUserInfo(String newPassword, String newPhoneNumber, String newAdress, Long id); 

}
