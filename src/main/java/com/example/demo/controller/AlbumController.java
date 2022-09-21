package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Album;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AlbumServices;

@Controller
public class AlbumController {

	@Autowired
	private AlbumServices albumServices;
	
	@Autowired UserRepository userRepository;
	@Autowired AlbumRepository albumRepository;
	
	@GetMapping("/ajout-album")
	public String ajoutAlbum() {
		return "/album/form";
	}
	
	@PostMapping("/ajout-album")
	public String ajoutAlbum(@Validated Album album, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return "album/form";
		}
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		if (username != null) {
			User user = new User();
			user = userRepository.findByEmail(username);
			
			album.setUser(user);
			
			albumServices.createAlbum(album);
			
			return "redirect:/";
		}
		
		return "album/ajout";
	}
	
	// liste de mess albums
	@GetMapping("/liste-albums")
	public String list(Model model) {
		
		
		List<Album> albums = albumServices.findAll();
		
		model.addAttribute("albums", albums);
		
		return "album/list";
		
	}
}
