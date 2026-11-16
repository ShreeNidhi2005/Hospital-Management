package com.example.loginapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginapp.service.LoginService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000" ,  "https://hospital-management-26yikv8ui-shreenidhi-projects.vercel.app")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");

		boolean isAuthenticated = loginService.loginAdmin(email, password);

		if (isAuthenticated) {
			return ResponseEntity.ok("Admin Login Successful");
		} else {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String newPassword = request.get("newPassword");

		String response = loginService.resetAdminPassword(email, newPassword);

		if ("Password reset successfully.".equals(response)) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}
}
