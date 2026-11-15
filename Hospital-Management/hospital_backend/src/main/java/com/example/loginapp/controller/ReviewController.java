package com.example.loginapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginapp.model.Review;
import com.example.loginapp.service.ReviewService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/appointments/{appointmentId}/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping
	public ResponseEntity<?> addReview(@PathVariable Long appointmentId, @RequestBody Review review) {
		try {
			Review createdReview = reviewService.addReview(appointmentId, review);
			return ResponseEntity.ok(createdReview);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> getReviewsByAppointment(@PathVariable Long appointmentId) {
		try {
			List<Review> reviews = reviewService.getReviewsByAppointmentId(appointmentId);
			return ResponseEntity.ok(reviews);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error retrieving reviews: " + e.getMessage());
		}
	}
}
