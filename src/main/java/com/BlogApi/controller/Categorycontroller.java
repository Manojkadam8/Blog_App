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
import com.BlogApi.payloads.CategoryDto;
import com.BlogApi.services.Categoryservice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class Categorycontroller {

	@Autowired
	Categoryservice categoryservice;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto categorydto){
		
		CategoryDto createdcat=this.categoryservice.CreateCategory(categorydto);
		return  new ResponseEntity<CategoryDto>(createdcat,HttpStatus.CREATED);
		
		
	}
	
	@PutMapping("/{catid}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categorydto , @PathVariable Integer catid){
		CategoryDto updatedcat=this.categoryservice.UpdateCategory(categorydto,catid);
		return  new ResponseEntity<CategoryDto>(updatedcat,HttpStatus.OK);
	}
	@DeleteMapping("/{catid}")
	public ResponseEntity<ApiResponce> DeleteCategory(@PathVariable Integer catid){
		this.categoryservice.DeleteCategory(catid);
		return  new ResponseEntity<ApiResponce>(new ApiResponce ("Category is Deleted",true),HttpStatus.OK);
	}

	@GetMapping("/{catid}")
	public ResponseEntity<CategoryDto> CategoryGetbyid(@PathVariable Integer catid){
		CategoryDto cat=this.categoryservice.CategoryGetById(catid);
		return  new ResponseEntity<CategoryDto>(cat,HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> GetAllCategory(){
		
		List<CategoryDto> allcategories=this.categoryservice.GetAllCategories();
		return  new ResponseEntity<List<CategoryDto>>(allcategories,HttpStatus.OK);
	}
}
