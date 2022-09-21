package com.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.model.UserInfo;
import com.springboot.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo=userRepository.getByUsernameOrEmail(username);
		String role =userInfo.getAccountType();
		List<GrantedAuthority> list=new ArrayList<>();
		SimpleGrantedAuthority sga=new SimpleGrantedAuthority(role);
		list.add(sga);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		
		//Fetch Raw/Clear text password from UserInfo 
		String rawPassword = userInfo.getPassword(); //this is a clear text password 
		//Encode the Raw password 
		String encodedPassword = passwordEncoder.encode(rawPassword);
		//Set encoded password  
		userInfo.setPassword(encodedPassword);
		
		return new User(userInfo.getUserName(),encodedPassword,list);
	}
	

}