package com.example.loginapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loginapp.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Optional<Doctor> findByNameAndProfessionAndSpecialization(String name, String profession, String specialization);
}
