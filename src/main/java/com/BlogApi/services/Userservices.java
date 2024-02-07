package com.BlogApi.services;

import java.util.List;

import com.BlogApi.payloads.UserDto;

public interface Userservices {

	UserDto CreateUser(UserDto userdto);
	UserDto UpadateUser(UserDto userdto , Integer Userdtoid);
	UserDto GetUserByid(Integer Userdtoid);
	List<UserDto> GetAllUser();
	void DeleteUser(Integer Userdtoid);
	
	UserDto RegisterUser(UserDto userDto);
}
