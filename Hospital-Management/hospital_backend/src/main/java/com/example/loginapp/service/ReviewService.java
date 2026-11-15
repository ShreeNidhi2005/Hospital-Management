package com.example.loginapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginapp.model.Appointment;
import com.example.loginapp.model.AppointmentStatus;
import com.example.loginapp.model.Review;
import com.example.loginapp.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private AppointmentService appointmentService;

	public Review addReview(Long appointmentId, Review review) {
		Appointment appointment = appointmentService.getAppointmentById(appointmentId)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		if (appointment.getStatus() != AppointmentStatus.APPROVED || !Boolean.TRUE.equals(appointment.getVisited())) {
			throw new RuntimeException("Review can only be added if the appointment is approved and visited.");
		}

		review.setDate(LocalDate.now());
		review.setAppointment(appointment);

		return reviewRepository.save(review);

	}

	public List<Review> getReviewsByAppointmentId(Long appointmentId) {
		return reviewRepository.findByAppointmentId(appointmentId);
	}

}
