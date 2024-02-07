package com.BlogApi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BlogApi.config.AppConstant;
import com.BlogApi.payloads.ApiResponce;
import com.BlogApi.payloads.PostDto;
import com.BlogApi.payloads.PostResponse;
import com.BlogApi.services.FileService;
import com.BlogApi.services.Postservice;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

	@Autowired
	private Postservice postservice;
    
	@Autowired
	FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/post/user/{UserId}/category/{CategoryId}")
	public ResponseEntity<PostDto> CreatePost(@Valid @RequestBody PostDto postdto,@PathVariable Integer UserId,@PathVariable Integer CategoryId){
		PostDto Createdpost=this.postservice.CreatePost(postdto, UserId, CategoryId);
		
		return new ResponseEntity<PostDto>(Createdpost,HttpStatus.CREATED);
	}
	
	//Update post
	@PutMapping("/post/{postid}")
	public ResponseEntity<PostDto> UpdatePost(@Valid @RequestBody PostDto postdto,@PathVariable Integer postid){
		PostDto Updatedpost=this.postservice.UpdatePost(postdto, postid);
		
		return new ResponseEntity<PostDto>(Updatedpost,HttpStatus.OK);
	}
	// delete post
	@DeleteMapping("/post/delete/{postid}")
	public ResponseEntity<ApiResponce> Deletepostbyid( @PathVariable Integer postid){
		
	  this.postservice.DeletePost(postid);		
		return new ResponseEntity<ApiResponce> (new ApiResponce("post  is Deleted", true),HttpStatus.OK);
	}
	
	
	//get post by id 
	@GetMapping("/post/{postid}")
	public ResponseEntity<PostDto> GetPostById(@PathVariable Integer postid){
		PostDto post=this.postservice.GetPostById(postid);
		
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	// Get All Posts
	@GetMapping("/post")
	public ResponseEntity<PostResponse> GetAllPost(
	        @RequestParam(value = "pagenumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pagenumber,
	        @RequestParam(value = "pagesize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pagesize,
	        @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
	        @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

	    try {
	        System.out.println("Received parameters:");
	        System.out.println("pagenumber: " + pagenumber);
	        System.out.println("pagesize: " + pagesize);
	        System.out.println("sortBy: " + sortBy);
	        System.out.println("sortDir: " + sortDir);

	        PostResponse posts = this.postservice.GetAllPost(pagenumber, pagesize, sortBy, sortDir);

	        return new ResponseEntity<>(posts, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	// Get Post By user
	
	@GetMapping("/post/user/{UserId}")
	public ResponseEntity<List<PostDto>> GetPostByUser(@PathVariable Integer UserId){
		List<PostDto> posts=this.postservice.GetPostByUser(UserId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/post/category/{categoryid}")
	public ResponseEntity<List<PostDto>> GetPostByCategory(@PathVariable Integer categoryid){
		System.out.println("cat : "+categoryid);
		List<PostDto> posts=this.postservice.GetPostByCategory(categoryid);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// search
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> SearchpostByTitle(@PathVariable String keyword){
		List<PostDto> results=this.postservice.Searchposts(keyword);
		
		return new ResponseEntity<List<PostDto>> (results,HttpStatus.OK);
		
	}
	
	//post image upload
	
	@PostMapping("/post/image/{postId}")
	public ResponseEntity<PostDto> UploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException {
		PostDto postdto=this.postservice.GetPostById(postId);
		
		String filename=this.fileservice.UploadImage(path, image);
		
            postdto.setImagename(filename);
		
            PostDto updatedpost=this.postservice.UpdatePost(postdto, postId);
		
		return new ResponseEntity<PostDto>(updatedpost,HttpStatus.OK);
		
	}
	
	// get image 
	
	@GetMapping(value="/post/image/{ImageName}" , produces= MediaType.IMAGE_JPEG_VALUE)
	public void DownloadImage(@PathVariable String ImageName,HttpServletResponse response) throws IOException {
		 InputStream resource = this.fileservice.GetResource(path, ImageName);
		 
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 
		 StreamUtils.copy(resource, response.getOutputStream());
		 
		
	}
	
}
