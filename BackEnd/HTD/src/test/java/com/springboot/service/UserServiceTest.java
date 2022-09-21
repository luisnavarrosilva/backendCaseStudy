package com.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.springboot.Exception.NotFoundException;
import com.springboot.dto.UserDto;
import com.springboot.model.UserInfo;
import com.springboot.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserService userService;
	
	private UserInfo userInfo;
	@Captor
	private ArgumentCaptor<UserInfo> userEntity;
	
	@BeforeEach
	void setUp() {
		
		userInfo=new UserInfo();
		userInfo.setId(22L);
		userInfo.setName("Luis");
		userInfo.setLastName("Navarro");
		userInfo.setEmail("luis.navarrosilva.90@gmail.com");
		userInfo.setUserName("luis123");
		userInfo.setPassword("nava12");
		userInfo.setPhoneNumber("2491375043");
		userInfo.setAdress("Tajin 226");
		userInfo.setAccountType("Client");
		
	}
	
	//--------------Add a new user to the db--------------------------------------------------------------------
	@Test
	void addUser() {
		//response
		ResponseEntity<String> expectedResponse=
				new ResponseEntity<>("User Saved Succesfully",HttpStatus.OK);
		//mock
		when(userRepository.save(any())).thenReturn(userInfo);
		//Convert to Dto
		UserDto userDto=mapToDto(userInfo);
		//Call the method from the service and saved the response
		ResponseEntity<String> response=userService.addUser(userDto);
		//Capture de user saved
		verify(userRepository).save(userEntity.capture());
		//assertion
		assertThat(response).isEqualTo(expectedResponse);
		assertThat(userEntity.getValue().getUserName()).isEqualTo(userInfo.getUserName());
		assertThat(userEntity.getValue().getEmail()).isEqualTo(userInfo.getEmail());

	}
	//---------------------Show all the users in the db	----------------------------------
	@Test
	void findAllUsers() {
		when(userRepository.findAll()).thenReturn(Arrays.asList(userInfo));
		assertNotNull(userService.findAllUsers());
	}
	 //-----------------Show a single user based on Username/email-------------------------
	@Test
	void fetchUserByUsername() {
		String userName=userInfo.getUserName();
		String email=userInfo.getEmail();
		//mock
		when(userRepository.getByUsernameOrEmail(userName)).thenReturn(userInfo);
		when(userRepository.getByUsernameOrEmail(email)).thenReturn(userInfo);
		//expected response
		ResponseEntity<UserInfo> expectedUser=new ResponseEntity<>(userInfo,HttpStatus.OK);
		//assertion
		assertThat(userService.fetchUser(userName)).isEqualTo(expectedUser);
		assertThat(userService.fetchUser(email)).isEqualTo(expectedUser);	
		//assert Exception
		String fakeUser="Fake user";
		assertThatExceptionOfType(NotFoundException.class)
        .isThrownBy(()->userService.fetchUser(fakeUser))        
        .withMessage(fakeUser+" no se encuentra en la base de datos");
	}
    //------------------update user based on id ----------------------------------
	@Test
	void updateUserById() {
		//mocks
		Long id=userInfo.getId();
		UserInfo newUser=userInfo;
		newUser.setPassword("newPassword");
		newUser.setPhoneNumber("789237489");
		newUser.setAdress("Hidalgo 21");
		

		userService.updateUser(id, mapToDto(newUser));
		verify(userRepository).updateUserInfo("newPassword", "789237489", "Hidalgo 21", id);
		
	}
	
	private UserDto mapToDto(UserInfo userInfo) {
		UserDto userDto=new UserDto();
		userDto.setLastName(userInfo.getLastName());
		userDto.setAdress(userInfo.getAdress());
		userDto.setAccountType(userInfo.getAccountType());
		userDto.setUserName(userInfo.getUserName());
		userDto.setEmail(userInfo.getEmail());
		userDto.setPassword(userInfo.getPassword());
		userDto.setPhoneNumber(userInfo.getPhoneNumber());
		userDto.setName(userInfo.getName());
		return userDto;
	}

}
