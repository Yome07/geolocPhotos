package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Album;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AlbumServices;
import com.example.demo.service.PhotoServices;
import com.example.demo.service.UserServices;

@Controller
public class AlbumController {
	
	@Autowired
	private UserServices userServices;

	@Autowired
	private AlbumServices albumServices;
	
	@Autowired
	private PhotoServices photoServices;
	
	@Autowired UserRepository userRepository;
	@Autowired AlbumRepository albumRepository;
	
	@GetMapping("/ajout-album")
	public String addAlbum() {
		return "/album/form";
	}
	
	@PostMapping("/ajout-album")
	public String addAlbum(@Validated Album album, BindingResult bindingResult) {
		
//		if (bindingResult.hasErrors()) {
//			System.out.println(bindingResult.hasErrors());
//			return "album/form";
//		}
		
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
	
	// liste de mes albums
	@GetMapping("/mes-albums")
	public String mesAlbums(Model model) {
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		User user = new User();
		user = userRepository.findByEmail(username);

		List<Album> mesAlbums = albumServices.getAlbumsByUserId(user.getId());

		model.addAttribute("mesAlbums", mesAlbums);
		
		return "album/mesAlbums";
		
	}
	
	// liste de tous les albums
	@GetMapping("/liste-albums")
	public String list(Model model) {
		
		List<Album> albums = albumServices.findAll();
		
		model.addAttribute("albums", albums);
		
		return "album/list";
		
	}
	
	// liste des photos par albums
	@GetMapping("/liste-photos-par-album/{id}")
	public String listPhotosByAlbum(Model model, @PathVariable(value="id") Long id) {
		List<Photo> photos = photoServices.getByAlbumId(id);
		
		model.addAttribute("photos", photos);
		System.out.println(photos);
		
		return "album/listPhotos";
	}
	
	
		
	//************EDIT
	@GetMapping("/edit-album")
	public String editAlbum(@RequestParam(value = "id", required = false) Long id,Model model) {
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userServices.findByEmail(username);
			
			
		}
		
		if(id != null) {
			Optional<Album> album = albumServices.getById(id);
			if(album.isPresent()) {
				model.addAttribute("album", album.get());
			}
		}
		
		
		return "album/editAlbum";
	}
	
	@PostMapping("/edit-album")
	public String editAlbum(@Validated Album album, BindingResult bindingResult,  
			@RequestParam("id") Optional<Long> id)  {
		
		
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			

			return "/album/ajoutAlbum";
		}
		
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userServices.findByEmail(username);
			
			album.setUser(user);
			
			albumServices.createAlbum(album);
			
			
			return "redirect:/mes-albums";
			
		}
		
		return "home";
	}	
	
	//*************DELETE
	@GetMapping("/mes-albums/delete/{id}")
	public String deleteAlbum(@PathVariable(value="id") Long id, Model model) {
	
		Optional<Album> album =albumServices.getById(id);
		
		List<Photo> photos = photoServices.getByAlbumId(id);
		
		if(photos.isEmpty() && album.isPresent()) {
			
			albumServices.deleteAlbum(album.get());
		}
		return "redirect:/mes-albums";
	}
}
