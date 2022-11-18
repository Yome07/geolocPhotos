package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryServices {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public void createCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}

	public Optional<Category> getById(Long id) {
		return categoryRepository.findById(id);
	}

	public List<Category> getByPhotoId(Long id) {
		return categoryRepository.findByPhotoId(id);
	}
}
