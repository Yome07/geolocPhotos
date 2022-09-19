package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;

@Service
public class AlbumServices {

	@Autowired
	private AlbumRepository albumRepository;
	
	public void createAlbum(Album album) {
		albumRepository.save(album);
	}
}
