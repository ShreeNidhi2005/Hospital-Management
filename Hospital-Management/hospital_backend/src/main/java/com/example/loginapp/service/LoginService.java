package com.example.loginapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginapp.model.User;
import com.example.loginapp.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	public boolean loginAdmin(String email, String password) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user != null && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public String resetAdminPassword(String email, String newPassword) {
		if (newPassword == null || newPassword.trim().isEmpty()) {
			return "New password cannot be empty.";
		}
		User user = userRepository.findByEmail(email).orElse(null);
		if (user != null) {
			user.setPassword(newPassword);
			userRepository.save(user);
			return "Password reset successfully.";
		} else {
			return "Email not found.";
		}
	}
}
