package com.BlogApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApi.payloads.ApiResponce;
import com.BlogApi.payloads.UserDto;
import com.BlogApi.services.Userservices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class Usercontroller {
     
	@Autowired
	Userservices userservices;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto){
		
		System.out.println(userdto);
		UserDto CreatedUserdto = this.userservices.CreateUser(userdto);
		
		return new ResponseEntity<>(CreatedUserdto,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDto> GetUserByid(@PathVariable("id") int id){
//		UserDto userbyid=this.userservices.GetUserByid(id);
//		System.out.println(userbyid);
		return  ResponseEntity.ok(this.userservices.GetUserByid(id));
		
		
	}
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>> GetAllUser(){
		List<UserDto> alluser=this.userservices.GetAllUser();
		alluser.forEach(e->System.out.println(e));
		return new ResponseEntity<>(alluser, HttpStatus.OK);
		
		
	}
	
	@PutMapping("/user/{userid}")
	public ResponseEntity<UserDto> UpadateUser(@Valid @RequestBody UserDto user , @PathVariable("userid") int id){
		UserDto UpdatedUser=this.userservices.UpadateUser(user,id); 
		System.out.println(UpdatedUser);
		return new ResponseEntity<>(UpdatedUser, HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/user/{userid}")
	public ResponseEntity<ApiResponce> DeleteUser(@PathVariable("userid") int id){
		this.userservices.DeleteUser(id);
//		System.out.println(DeletedUser);
		return new ResponseEntity<>(new ApiResponce("User deleted successfully", true),HttpStatus.OK);
		
		
	}
}
