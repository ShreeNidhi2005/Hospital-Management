package com.example.loginapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loginapp.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("SELECT a FROM Appointment a WHERE a.patientName = :patientName AND a.patientDob = :patientDob")
	Optional<Appointment> findByPatientNameAndPatientDob(@Param("patientName") String patientName,
			@Param("patientDob") LocalDate patientDob);

	@Query("SELECT a FROM Appointment a WHERE a.patientName = :patientName AND a.patientDob = :patientDob ORDER BY a.appointmentDate DESC, a.appointmentTime DESC")
	List<Appointment> findAllByPatientNameAndPatientDob(@Param("patientName") String patientName,
			@Param("patientDob") LocalDate patientDob);
}