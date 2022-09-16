package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long>{

}
