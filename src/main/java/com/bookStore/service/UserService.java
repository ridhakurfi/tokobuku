package com.bookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository uRepo;

	public boolean validateUsername(String username) {

		return uRepo.validateUsername(username);
	}

	public boolean validatePassword(String password) {

		return uRepo.validatePassword(password);
	}
}
