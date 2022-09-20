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
			
			return "/photo/ajoutPhoto";
		}
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		System.out.println(username);
		if (username != null) {
			User user = new User();
			user = userRepository.findByEmail(username);
			System.out.println(user);
			photo.setUser(user);
			
			photoServices.createPhoto(photo);
			
			return "redirect:/";
			
		}
		
		return "photo/ajoutPhoto";
	}
}
