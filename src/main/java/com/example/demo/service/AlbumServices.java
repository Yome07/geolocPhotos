package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Album;
import com.example.demo.model.Photo;
import com.example.demo.repository.AlbumRepository;

@Service
public class AlbumServices {

	@Autowired
	private AlbumRepository albumRepository;
	
	public Album createAlbum(Album album) {
		return albumRepository.save(album);
	}
	
	public void deleteAlbum(Album album) {
		albumRepository.delete(album);
	}
	
	public List<Album> findAll(){
		return albumRepository.findAll();
	}
	
	public Optional<Album> getById(Long id) {
		return albumRepository.findById(id);
	}
	
	public List<Album> getAlbumsByUserId(Long id) {
		return albumRepository.findByUserId(id);
	}
}
