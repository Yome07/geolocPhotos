package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Photo;
import com.example.demo.service.PhotoServices;

@Controller
public class PhotoController {

	@Autowired
	private PhotoServices photoServices;
	
	@GetMapping("/ajoutPhoto")
	public String photos(Photo photo) {
		return "photo/ajoutPhoto";
	}
}
