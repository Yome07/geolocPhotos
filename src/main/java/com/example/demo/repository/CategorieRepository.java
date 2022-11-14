package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	List<Categorie> findByPhotoId(Long id);
}
