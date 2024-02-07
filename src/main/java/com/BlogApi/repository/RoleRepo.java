package com.BlogApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApi.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
