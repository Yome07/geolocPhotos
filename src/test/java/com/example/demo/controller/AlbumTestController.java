package com.example.demo.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Album;
import com.example.demo.service.AlbumServices;

@DataJpaTest
class AlbumTestController {
	
	@Autowired
	private AlbumServices albumServices;

	@Test
	void createAlbumTest() {
		//fail("Not yet implemented");
		
		Album album = new Album("enfants");
		
		Album savedAlbum = albumServices.createAlbum(album);
		
		assertNotNull(savedAlbum);
		
		//Assertions.assertEquals(true,albumServices.createAlbum(album));

	}
	
	/*
	 * @Test void testFindAlbumByName() { String name = }
	 */

}
