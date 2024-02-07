package com.BlogApi.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApi.entities.Comment;
import com.BlogApi.entities.Post;
import com.BlogApi.exeption.ResourceNotFoundException;
import com.BlogApi.payloads.CommentDto;
import com.BlogApi.repository.CommentRepo;
import com.BlogApi.repository.PostRepo;
import com.BlogApi.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepo commentrepo;
	
	@Autowired
	PostRepo postrepo;
	
	@Autowired
	ModelMapper Modelmapper;
	@Override
	public CommentDto CreateComment(CommentDto commetdto, Integer postid) {
		Post post=this.postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("post", "post id", postid));
		
		Comment comment=this.Modelmapper.map(commetdto, Comment.class);
		
		comment.setPost(post);
	
		Comment createdcomment=this.commentrepo.save(comment);
		
		
		return this.Modelmapper.map(createdcomment, CommentDto.class);
	}

	@Override
	public void Deletecomment(Integer commentid) {
		
		Comment com=this.commentrepo.findById(commentid).orElseThrow(()->new ResourceNotFoundException("Comment", "comment id", commentid));
		
		this.commentrepo.delete(com);

	}

}
