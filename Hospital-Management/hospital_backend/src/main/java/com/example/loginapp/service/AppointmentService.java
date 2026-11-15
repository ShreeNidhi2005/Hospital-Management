package com.example.loginapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginapp.model.Appointment;
import com.example.loginapp.model.AppointmentStatus;
import com.example.loginapp.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public Optional<Appointment> findAppointmentByPatientDetails(String patientName, LocalDate patientDob) {
		return appointmentRepository.findByPatientNameAndPatientDob(patientName, patientDob);
	}

	public List<Appointment> findAllAppointmentsByPatientDetails(String patientName, LocalDate patientDob) {
		return appointmentRepository.findAllByPatientNameAndPatientDob(patientName, patientDob);
	}

	public Appointment createAppointment(Appointment appointment) {
		appointment.setCreatedAt(LocalDateTime.now());
		appointment.setUpdatedAt(LocalDateTime.now());
		return appointmentRepository.save(appointment);
	}

	public List<Appointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}

	public Optional<Appointment> getAppointmentById(Long id) {
		return appointmentRepository.findById(id);
	}

	public Appointment updateAppointment(Long id, Appointment appointment) {
		if (appointmentRepository.existsById(id)) {
			appointment.setId(id);
			appointment.setUpdatedAt(LocalDateTime.now());
			return appointmentRepository.save(appointment);
		}
		return null;
	}

	public Appointment changeAppointmentStatus(Long id, AppointmentStatus status) {
		Optional<Appointment> existingAppointment = appointmentRepository.findById(id);
		if (existingAppointment.isPresent()) {
			Appointment appointment = existingAppointment.get();
			appointment.setStatus(status);
			appointment.setUpdatedAt(LocalDateTime.now());
			return appointmentRepository.save(appointment);
		}
		return null;
	}

	public boolean deleteAppointment(Long id) {
		if (appointmentRepository.existsById(id)) {
			appointmentRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public Appointment markVisit(Long id, Boolean visited) {
		Optional<Appointment> existingAppointment = appointmentRepository.findById(id);
		if (existingAppointment.isPresent()) {
			Appointment appointment = existingAppointment.get();
			appointment.setVisited(visited);
			appointment.setUpdatedAt(LocalDateTime.now());
			return appointmentRepository.save(appointment);
		}
		return null;
	}

	public Appointment setRejectionReason(Long id, String rejectionReason) {
		Optional<Appointment> existingAppointment = appointmentRepository.findById(id);
		if (existingAppointment.isPresent()) {
			Appointment appointment = existingAppointment.get();
			appointment.setRejectionReason(rejectionReason);
			appointment.setStatus(AppointmentStatus.REJECTED);
			appointment.setUpdatedAt(LocalDateTime.now());
			return appointmentRepository.save(appointment);
		}
		return null;
	}
}