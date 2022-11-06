package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{

	List<Photo> findByPublique(boolean publique);

	List<Photo> findByUserId(Long id);
}
