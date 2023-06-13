package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookStore.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

	public boolean validateUsername(String username);
	public boolean validatePassword(String password);
}
