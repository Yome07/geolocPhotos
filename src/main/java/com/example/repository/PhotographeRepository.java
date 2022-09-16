package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Photographe;

public interface PhotographeRepository extends JpaRepository<Photographe, Long> {
	Photographe findByEmail(String email);
}
