package com.BlogApi.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApi.Security.CustomUserDetailService;
import com.BlogApi.Security.JwtTokenHelper;
import com.BlogApi.entities.User;
import com.BlogApi.exeption.ApiException;
import com.BlogApi.payloads.JwtAuthRequest;
import com.BlogApi.payloads.JwtAuthResponse;
import com.BlogApi.payloads.UserDto;
import com.BlogApi.repository.Userrepo;
import com.BlogApi.services.Userservices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Userservices userservice;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> CreateToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		System.out.println(request.getUsername() + "username");
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails=this.userDetailService.loadUserByUsername(request.getUsername());
		
		String token =this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		
		response.setToken(token);
		response.setUser(this.modelMapper.map((User) userDetails ,UserDto.class));
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			
			throw new ApiException("Invalid Username or password !!");
		}
		
	}
	 @ExceptionHandler(BadCredentialsException.class)
	    public String exceptionHandler() {
	        return "Credentials Invalid !!";
	    }
	 
	 
	 @PostMapping("/register")
	 public ResponseEntity<UserDto> RigisterNewUser(@Valid @RequestBody UserDto userdto){
		 
		 UserDto registeruser=this.userservice.RegisterUser(userdto);
		 
		 return new ResponseEntity<UserDto>(registeruser,HttpStatus.CREATED);
		 
	 }
}
