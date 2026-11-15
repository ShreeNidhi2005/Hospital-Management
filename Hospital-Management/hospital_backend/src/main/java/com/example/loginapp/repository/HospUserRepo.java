package com.example.loginapp.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loginapp.model.HospUser;

@Repository
public interface HospUserRepo extends JpaRepository<HospUser, Integer> {

	Optional<HospUser> findByUsername(String username);

	Optional<HospUser> findByDob(LocalDate dob);

	boolean existsByUsername(String username);
}
