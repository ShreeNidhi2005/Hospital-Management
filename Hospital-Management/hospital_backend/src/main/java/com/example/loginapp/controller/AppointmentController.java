package com.example.loginapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginapp.model.Appointment;
import com.example.loginapp.model.AppointmentStatus;
import com.example.loginapp.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping("/create")
	public ResponseEntity<?> createAppointment(@RequestBody @Validated Appointment appointment) {
		Optional<Appointment> existingAppointment = appointmentService
				.findAppointmentByPatientDetails(appointment.getPatientName(), appointment.getPatientDob());

		if (existingAppointment.isPresent()) {
			List<Appointment> previousAppointments = appointmentService
					.findAllAppointmentsByPatientDetails(appointment.getPatientName(), appointment.getPatientDob());

			return ResponseEntity.ok(previousAppointments);
		} else {
			Appointment createdAppointment = appointmentService.createAppointment(appointment);
			return ResponseEntity.ok(createdAppointment);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Appointment>> getAllAppointments() {
		return ResponseEntity.ok(appointmentService.getAllAppointments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
		Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
		return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id,
			@RequestBody @Validated Appointment appointment) {
		Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
		return updatedAppointment != null ? ResponseEntity.ok(updatedAppointment) : ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<Appointment> changeAppointmentStatus(@PathVariable Long id,
			@RequestParam AppointmentStatus status) {
		Appointment updatedAppointment = appointmentService.changeAppointmentStatus(id, status);
		return updatedAppointment != null ? ResponseEntity.ok(updatedAppointment) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
		boolean isDeleted = appointmentService.deleteAppointment(id);
		return isDeleted ? ResponseEntity.ok("Appointment deleted successfully") : ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}/visited")
	public ResponseEntity<Appointment> markVisit(@PathVariable Long id, @RequestParam Boolean visited) {
		Appointment updatedAppointment = appointmentService.markVisit(id, visited);
		return updatedAppointment != null ? ResponseEntity.ok(updatedAppointment) : ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}/reject")
	public ResponseEntity<Appointment> rejectAppointment(@PathVariable Long id, @RequestParam String rejectionReason) {
		Appointment updatedAppointment = appointmentService.setRejectionReason(id, rejectionReason);
		return updatedAppointment != null ? ResponseEntity.ok(updatedAppointment) : ResponseEntity.notFound().build();
	}

}
