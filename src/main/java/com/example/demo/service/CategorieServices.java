package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Album;
import com.example.demo.model.Categorie;
import com.example.demo.repository.CategorieRepository;

@Service
public class CategorieServices {
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	public void createCategorie(Categorie categorie) {
		categorieRepository.save(categorie);
	}
	
	public void deleteCategorie(Categorie categorie) {
		categorieRepository.delete(categorie);
	}
	
	public List<Categorie> findAll(){
		return categorieRepository.findAll();
	}

	public Optional<Categorie> getById(Long id) {
		return categorieRepository.findById(id);
	}

	public List<Categorie> getByPhotoId(Long id) {
		return categorieRepository.findByPhotoId(id);
	}
}
