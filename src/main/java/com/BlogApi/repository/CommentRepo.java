package com.BlogApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApi.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
