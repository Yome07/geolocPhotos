package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.PhotoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PhotoServices;

@Controller
public class PhotoController {

	@Autowired
	private PhotoServices photoServices;
	
	@Autowired UserRepository userRepository;
	@Autowired PhotoRepository photoRepository;
	
	@GetMapping("/ajout-photo")
	public String ajoutPhoto() {
		
		return "photo/ajoutPhoto";
	}
	
	@PostMapping("/ajout-photo")
	public String ajoutPhoto(@Validated Photo photo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			System.out.println(bindingResult.getObjectName());
			System.out.println(photo.getDescription());
//			System.out.println(photo.getFileName());
			System.out.println(photo.getLatitude());
			System.out.println(photo.getLongitude());
			System.out.println(photo.getTitre());
			System.out.println(photo.isPublique());
			System.out.println(photo.getDate());
			return "/photo/ajoutPhoto";
		}
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userRepository.findByEmail(username);
			
			photo.setUser(user);
			
			photoServices.createPhoto(photo);
			
			return "redirect:/";
			
		}
		
		return "photo/ajoutPhoto";
	}
}
