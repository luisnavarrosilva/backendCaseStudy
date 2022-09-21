package com.springboot.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.springboot.Exception.DuplicatedElementException;
import com.springboot.Exception.NotFoundException;
import com.springboot.dto.UserDto;
import com.springboot.model.UserInfo;
import com.springboot.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailSenderService senderService;
	
	//--------------Add a new user to the db--------------------------------------------------------------------
	public ResponseEntity<String> addUser(UserDto userDto) {
		
		UserInfo user=mapToEntity(userDto);
		//Check if the user already exist in the db
		UserInfo u=userRepository.getByUsernameOrEmail(user.getUserName(),user.getEmail());
		if(u!=null)
			throw new DuplicatedElementException("This username/email already exist");
		//Save UserInfo in DB
		userRepository.save(user);
		return new ResponseEntity<>("User Saved Succesfully",HttpStatus.OK);
	}
	//---------------------Show all the users in the db	----------------------------------
	public List<UserInfo> findAllUsers() {
			return userRepository.findAll();
		}
    //-----------------Show a single user based on Username/email-------------------------
	public ResponseEntity<UserInfo> fetchUser(String userName) {
		UserInfo user=userRepository.getByUsernameOrEmail(userName);
		if(user==null) 
			throw new NotFoundException(userName+" no se encuentra en la base de datos");
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
    //------------------update user based on id ----------------------------------
	public String updateUser(Long id, UserDto newUser) {
		String encodedPassword = newUser.getPassword();
		String newPassword=encodedPassword;
		String newPhoneNumber=newUser.getPhoneNumber();
		String newAdress=newUser.getAdress();
		userRepository.updateUserInfo(newPassword,newPhoneNumber,newAdress,id);
		return "User updated succesfully";
	}
    //----------------Send password to user by given email----------------------------------
	public String sendPasswordByEmail(String email) {
		UserInfo user=userRepository.getByUsernameOrEmail(email);
		if(user==null)
			throw new NotFoundException(email+" no se encuentra en la base de datos");
		senderService.sendEmail(email, "Login Password", 
					"Your Password is: "+user.getPassword());
			return "Password sent to given email";
	}
    //----------------Mapping method----------------------------------
	private UserInfo mapToEntity(UserDto userDto) {
		UserInfo userInfo=new UserInfo();
		userInfo.setLastName(userDto.getLastName());
		userInfo.setAdress(userDto.getAdress());
		userInfo.setAccountType(userDto.getAccountType());
		userInfo.setUserName(userDto.getUserName());
		userInfo.setEmail(userDto.getEmail());
		userInfo.setPassword(userDto.getPassword());
		userInfo.setPhoneNumber(userDto.getPhoneNumber());
		userInfo.setName(userDto.getName());
		return userInfo;
	}

	
}
