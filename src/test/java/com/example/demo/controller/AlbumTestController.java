package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Album;
import com.example.demo.service.AlbumServices;

class AlbumTestController {
	
	@Autowired
	private AlbumServices albumServices;

	@Test
	void createAlbumTest() {
		//fail("Not yet implemented");
		
		Album album = new Album("enfants");
		
		albumServices.createAlbum(album);
		
		//assertEquals(true,albumServices.createAlbum(album));

	}

}
