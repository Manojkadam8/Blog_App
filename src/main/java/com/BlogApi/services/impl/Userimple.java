package com.BlogApi.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BlogApi.config.AppConstant;
import com.BlogApi.entities.Role;
import com.BlogApi.entities.User;
import com.BlogApi.exeption.ResourceNotFoundException;
import com.BlogApi.payloads.UserDto;
import com.BlogApi.repository.RoleRepo;
import com.BlogApi.repository.Userrepo;
import com.BlogApi.services.Userservices;
@Service
public class Userimple implements Userservices {

	
	@Autowired
	private Userrepo userrepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepo Rolerepo;
	
	@Override
	public UserDto CreateUser(UserDto userdto) {
		User user=this.UserDtoToUser(userdto);
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		User saveduser=this.userrepo.save(user);
		return this.UserToUserDto(saveduser);
	}

	@Override
	public UserDto UpadateUser(UserDto userdto, Integer Userdtoid) {
	User user =this.userrepo.findById(Userdtoid).orElseThrow(()-> new ResourceNotFoundException("User","id",Userdtoid));
		user.setFullname(userdto.getFullname());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		User updateduser = this.userrepo.save(user);
		UserDto userdto1 =this.UserToUserDto(updateduser);
	return userdto1;
	}

	@Override
	public UserDto GetUserByid(Integer Userdtoid) {
		User user=this.userrepo.findById(Userdtoid).orElseThrow(()-> new ResourceNotFoundException("User","id",Userdtoid));
		
		
		return this.UserToUserDto(user);
	}

	@Override
	public List<UserDto> GetAllUser() {
		List<User> users=this.userrepo.findAll();
		
	List<UserDto> userdtos=	users.stream().map(user->this.UserToUserDto(user)).collect(Collectors.toList());
		
	return userdtos;
	}

	@Override
	public void DeleteUser(Integer Userdtoid) {
	User user=	this.userrepo.findById(Userdtoid).orElseThrow(()-> new ResourceNotFoundException("User","id",Userdtoid));
		
		this.userrepo.delete(user);

	}
    private User UserDtoToUser(UserDto userdto) {
    	User user= this.modelMapper.map(userdto,User.class);
//    	user.setId(userdto.getId());
//    	user.setFullname(userdto.getFullname());
//    	user.setEmail(userdto.getEmail());
//    	user.setPassword(userdto.getPassword());
    	
    	return user;
    	
    }
    
    private UserDto UserToUserDto(User user) {
    	
    	UserDto userdto=this.modelMapper.map(user, UserDto.class);
//    	userdto.setId(user.getId());
//    	userdto.setFullname(user.getFullname());
//    	userdto.setEmail(user.getEmail());
//    	userdto.setPassword(user.getPassword());
    	
    	return userdto;
    	
    }

	@Override
	public UserDto RegisterUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role=this.Rolerepo.findById(AppConstant.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User NEwUser=this.userrepo.save(user);
		return this.modelMapper.map(NEwUser, UserDto.class);
	}
}
