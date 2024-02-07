package com.BlogApi.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.BlogApi.entities.Category;
import com.BlogApi.entities.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {


	private Integer postid;
	
	@NotBlank
	@Size(min=10,message="Title is To Short")
	private String posttitle;
	

	@NotBlank
	@Size(min=20,message="Content is To Short")
	private String content;
	
	private String imagename;
	
	private Date   addedDate;
	
	
	private CategoryDto  category;
	
	
	private UserDto user;
	
	private Set<CommentDto> comment=new HashSet<>();

	
}
