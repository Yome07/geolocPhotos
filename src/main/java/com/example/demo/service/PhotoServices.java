package com.example.demo.service;

import java.util.List;

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
	
	public List<Photo> findAll() {
		return photoRepository.findAll();
	}
}
