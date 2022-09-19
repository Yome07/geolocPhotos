package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Photo;
import com.example.demo.repository.PhotoRepository;

@Service
public class PhotoServices {
	
	@Autowired
	private PhotoRepository photoRepository;
	
	public void createPhoto(Photo photo) {
		photoRepository.save(photo);
	}
}
