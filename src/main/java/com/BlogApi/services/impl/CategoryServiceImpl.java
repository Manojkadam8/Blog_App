package com.BlogApi.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApi.entities.Category;
import com.BlogApi.exeption.ResourceNotFoundException;
import com.BlogApi.payloads.CategoryDto;
import com.BlogApi.payloads.UserDto;
import com.BlogApi.repository.CategoryRepo;
import com.BlogApi.services.Categoryservice;
@Service
public class CategoryServiceImpl implements Categoryservice {
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto CreateCategory(CategoryDto categorydto) {
		Category cat=this.modelMapper.map(categorydto, Category.class);
		
		Category createdCat=this.categoryrepo.save(cat);
		
		return this.modelMapper.map(createdCat, CategoryDto.class);
	}

	@Override
	public CategoryDto UpdateCategory(CategoryDto categorydto, Integer Catid) {
		Category cat=this.categoryrepo.findById(Catid).orElseThrow(()-> new ResourceNotFoundException("category" , "category id" , Catid));
		cat.setCategorytitle(categorydto.getCategorytitle());
		cat.setCategorydescription(categorydto.getCategorydescription());
		 Category updatedcat=this.categoryrepo.save(cat);
		 
		return this.modelMapper.map(updatedcat,CategoryDto.class);
	}

	@Override
	public void DeleteCategory(Integer Catid) {
       Category cat=this.categoryrepo.findById(Catid).orElseThrow(()-> new  ResourceNotFoundException("category","category id" , Catid));
        
       this.categoryrepo.delete(cat);
	
	}

	@Override
	public CategoryDto CategoryGetById(Integer Catid) {
       Category cat=this.categoryrepo.findById(Catid).orElseThrow(()-> new  ResourceNotFoundException("category","category id" , Catid));
       
       

		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> GetAllCategories() {

      List<Category> categories=this.categoryrepo.findAll();
      
     
      List<CategoryDto> catdtos=categories.stream().map(cat->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		
      return catdtos;
	}

}
