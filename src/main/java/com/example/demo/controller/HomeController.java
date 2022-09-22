package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Photo;
import com.example.demo.repository.PhotoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PhotoServices;

@Controller
public class HomeController {
	
	@Autowired
	private PhotoServices photoServices;
	
	@Autowired UserRepository userRepository;
	@Autowired PhotoRepository photoRepository;
	

	//Affichage des photos avec marqueurs
		@GetMapping("/")
		public String listPhotos(Model model) {
			List<Photo> photos = photoServices.findAll();
			
			model.addAttribute("photos", photos);
			
			return "home";
		}
}
