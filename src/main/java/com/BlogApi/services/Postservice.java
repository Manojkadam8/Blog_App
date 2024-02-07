package com.BlogApi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.BlogApi.payloads.PostDto;
import com.BlogApi.payloads.PostResponse;

public interface Postservice {

	
	//create 
	
	PostDto CreatePost(PostDto postdto,Integer userid, Integer categoryid);
	
	
	//update 
	
	PostDto UpdatePost(PostDto postdto,Integer postid);
	
	//delete
	
	void DeletePost(Integer postid);
	
	//get All Posts
	
	PostResponse GetAllPost(Integer pageNumber,Integer PageSize,String sortBy,String sortDir); 
	
	
	//get post
	
	PostDto GetPostById(Integer postid);
	
	
	//Get All Post By category
	List<PostDto> GetPostByCategory(Integer categoryid);
	
	//Get All Post By user
	List<PostDto> GetPostByUser(Integer userid);
	
	//search posts
	
	List<PostDto> Searchposts(String keywords);
	
}
