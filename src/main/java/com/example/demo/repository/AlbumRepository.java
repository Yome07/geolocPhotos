package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Album;
import com.example.demo.model.User;

public interface AlbumRepository extends JpaRepository<Album, Long>{
	Album findById(String id);
}
