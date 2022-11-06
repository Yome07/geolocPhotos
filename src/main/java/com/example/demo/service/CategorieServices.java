package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Categorie;
import com.example.demo.repository.CategorieRepository;

@Service
public class CategorieServices {
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	public void createCategorie(Categorie categorie) {
		categorieRepository.save(categorie);
	}
	
	public List<Categorie> findAll(){
		return categorieRepository.findAll();
	}

}
