package com.BlogApi.services;

import java.util.List;

import com.BlogApi.payloads.CategoryDto;

public interface Categoryservice {
    
	
	//create 
	CategoryDto CreateCategory(CategoryDto categorydto);
	
	//update 
     CategoryDto UpdateCategory(CategoryDto categorydto , Integer Catid);
     
     
   //delete
 	void DeleteCategory(Integer Catid);
 	
 	//getbyid
 	CategoryDto CategoryGetById(Integer Catid);
 		
 	//get All Categories
 	List<CategoryDto> GetAllCategories();
 		
 		
}
