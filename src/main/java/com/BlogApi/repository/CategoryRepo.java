package com.BlogApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApi.entities.Category;

public interface CategoryRepo  extends JpaRepository <Category , Integer>{

}
