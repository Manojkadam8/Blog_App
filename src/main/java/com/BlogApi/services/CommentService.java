package com.BlogApi.services;

import com.BlogApi.payloads.CommentDto;

public interface CommentService {
	
	CommentDto CreateComment (CommentDto commetdto , Integer postid); 
	void Deletecomment(Integer commentid);

}
