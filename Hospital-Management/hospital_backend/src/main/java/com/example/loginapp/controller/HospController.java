package com.example.loginapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginapp.model.HospUser;
import com.example.loginapp.service.HospService;

@RestController
@RequestMapping("/hosp")
@CrossOrigin(origins = "http://localhost:3000")
public class HospController {

	@Autowired
	private HospService hospService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody HospUser user) {
		String result = hospService.registerUser(user);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody HospUser user) {
		String result = hospService.loginUser(user);
		return ResponseEntity.ok(result);
	}
}
