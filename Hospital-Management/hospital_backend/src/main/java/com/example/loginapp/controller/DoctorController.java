package com.example.loginapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginapp.model.Doctor;
import com.example.loginapp.service.DoctorService;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:3000" ,  "https://hospital-management-26yikv8ui-shreenidhi-projects.vercel.app")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;

	@PostMapping("/add")
	public ResponseEntity<String> addDoctor(@RequestBody Doctor doctor) {
		String result = doctorService.addDoctor(doctor);
		if ("Doctor added successfully!".equals(result)) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
		String result = doctorService.deleteDoctor(id);
		if ("Doctor deleted successfully!".equals(result)) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.badRequest().body(result);
		}
	}
}
