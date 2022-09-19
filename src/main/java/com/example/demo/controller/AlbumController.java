package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Album;
import com.example.demo.service.AlbumServices;

@Controller
public class AlbumController {

	@Autowired
	private AlbumServices albumServices;
	
	@GetMapping("/album")
	public String album(Album album) {
		return "/album/album";
	}
	
	@PostMapping("/album")
	public String ajoutAlbum(Album album, BindingResult bindingResult, Model model) {
		
		model.addAttribute("album", new Album());
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return "album/album";
		}
		
		albumServices.createAlbum(album);
		return "redirect:/album";
	}
}
