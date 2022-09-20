package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
	
}
