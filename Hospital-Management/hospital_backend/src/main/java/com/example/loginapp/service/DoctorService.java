package com.example.loginapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginapp.model.Doctor;
import com.example.loginapp.repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	public String addDoctor(Doctor doctor) {
		try {
			doctorRepository.save(doctor);
			return "Doctor added successfully!";
		} catch (Exception e) {
			return "Failed to add doctor";
		}
	}

	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	public String deleteDoctor(Long id) {
		if (doctorRepository.existsById(id)) {
			doctorRepository.deleteById(id);
			return "Doctor deleted successfully!";
		} else {
			return "Doctor not found";
		}
	}
}
