package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.model.Album;
import com.example.demo.model.User;
import com.example.demo.repository.AlbumRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AlbumTests {

	@Autowired
	private AlbumRepository albumRepository;
	
	@Test
	@Rollback(false)
	public void testCreateAlbum() {
		User user = new User("email@test.fr");
		Album album = new Album("Italie ’22", user);
		Album savedAlbum = albumRepository.save(album);
		
		assertNotNull(savedAlbum);
	}

}
