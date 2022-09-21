package com.springboot.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.UserDto;
import com.springboot.model.UserInfo;
import com.springboot.service.UserService;

@CrossOrigin
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	//--------------Add a new user to the db---------------------------------------------
	
	@PostMapping("/user") 
	public ResponseEntity<String> addUser(@RequestBody UserDto userDto) { 
		return userService.addUser(userDto);
	}
	//----------------Show all the users in the db	----------------------------------
	@GetMapping("/user")
	public ResponseEntity<List<UserInfo>> fetchAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}
    //-----------------Show a single user based on Username/email-------------------------
	@GetMapping("/login/{user}")
	public ResponseEntity<UserInfo> fetchUser(@PathVariable("user") String userName) {
		return userService.fetchUser(userName); 	
	
	}
    //------------------update user based on id ----------------------------------
	@PutMapping("user/{id}")
	public String updateUser(@PathVariable("id") Long id,@RequestBody UserDto newUser) {
		return userService.updateUser(id,newUser);
	}
    //----------------Send password to user by given email----------------------------------
	@GetMapping("user/forgottenPassword/{email}") 
	public String sendPasswordByEmail(@PathVariable("email") String email) {
		return userService.sendPasswordByEmail(email);
		
	}
}