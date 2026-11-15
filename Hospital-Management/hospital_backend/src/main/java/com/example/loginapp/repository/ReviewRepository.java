package com.example.loginapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loginapp.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByAppointmentId(Long appointmentId);

}
