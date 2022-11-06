package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Photo;
import com.example.demo.repository.PhotoRepository;

@Service
public class PhotoServices {
	
	@Autowired
	private PhotoRepository photoRepository;
	
	public Photo createPhoto(Photo photo) {
		return photoRepository.save(photo);
	}
	
	public void deletePhoto(Photo photo) {
		photoRepository.delete(photo);
	}
	
	public List<Photo> findAll() {
		return photoRepository.findAll();
	}
	
	public List<Photo> getByPublique(boolean publique) {
		return photoRepository.findByPublique(publique);
	}
	
	public List<Photo> getByUserId(Long id) {
		return photoRepository.findByUserId(id);
	}
	
	public Optional<Photo> getById(Long id) {
		return photoRepository.findById(id);
	}

}
