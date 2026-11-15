package com.example.loginapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginapp.model.HospUser;
import com.example.loginapp.repository.HospUserRepo;

@Service
public class HospService {

	@Autowired
	private HospUserRepo hospUserRepo;

	public String registerUser(HospUser user) {
		if (hospUserRepo.existsByUsername(user.getUsername())) {
			return "User already exists";
		}

		hospUserRepo.save(user);
		return "User registered successfully";
	}

	public String loginUser(HospUser user) {
		Optional<HospUser> existingUser = hospUserRepo.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			if (existingUser.get().getDob().equals(user.getDob())) {
				return "Login successful";
			} else {
				return "Invalid DOB";
			}
		} else {
			return "User not found";
		}
	}
}
