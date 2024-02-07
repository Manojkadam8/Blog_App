package com.BlogApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApi.payloads.ApiResponce;
import com.BlogApi.payloads.CommentDto;
import com.BlogApi.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
  CommentService commentservice;
	
	
	@PostMapping("/post/{postid}/comments")
	public ResponseEntity<CommentDto> Createcomments(@RequestBody CommentDto commentdto, @PathVariable Integer postid){
		
		CommentDto comcreated=this.commentservice.CreateComment(commentdto, postid);
		
		return new ResponseEntity<CommentDto> (comcreated,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentid}")
	public ResponseEntity<ApiResponce> Deletecomments( @PathVariable Integer commentid){
		
		this.commentservice.Deletecomment(commentid);		
		return new ResponseEntity<ApiResponce> (new ApiResponce("Comment is Deleted", true),HttpStatus.OK);
	}
}
