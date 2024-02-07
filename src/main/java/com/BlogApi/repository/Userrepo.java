package com.BlogApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApi.entities.User;

public interface Userrepo extends JpaRepository<User ,Integer>{

	Optional<User> findByEmail(String email);
}
