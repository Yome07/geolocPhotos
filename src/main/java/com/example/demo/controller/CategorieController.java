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
import com.example.demo.model.Categorie;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CategorieServices;
import com.example.demo.service.PhotoServices;

@Controller
public class CategorieController {

	@Autowired
	private CategorieServices categorieServices;
	
	@Autowired
	private PhotoServices photoServices;
	
	@Autowired UserRepository userRepository;
	@Autowired CategorieRepository categorieRepository;
	
	@GetMapping("/ajout-categorie")
	public String ajoutCategorie() {
		return "/categorie/form";
	}
	
	@PostMapping("/ajout-categorie")
	public String ajoutCategorie(@Validated Categorie categorie, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return "album/form";
		}
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		if (username != null) {
			
			categorieServices.createCategorie(categorie);
			
			return "redirect:/";
		}
		
		return "categorie/ajout";
	}
	
	// liste de toutes les catégories
	@GetMapping("/liste-categories")
	public String list(Model model) {
		
		
		List<Categorie> categories = categorieServices.findAll();
		
		model.addAttribute("categories", categories);
		
		return "categorie/list";
		
	}
	
	// liste des photos par catégorie
	@GetMapping("/liste-photos-par-categorie/{id}")
	public String listPhotosByCategorie(Model model, @PathVariable(value="id") Long id) {
		List<Photo> photos = photoServices.getByCategorieId(id);
		
		model.addAttribute("photos", photos);
		System.out.println(photos);
		
		return "categorie/listPhotos";
	}
	
	//************EDIT
		@GetMapping("/edit-categorie")
		public String editCategorie(@RequestParam(value = "id", required = false) Long id,Model model) {
			
			if(id != null) {
				Optional<Categorie> categorie = categorieServices.getById(id);
				if(categorie.isPresent()) {
					model.addAttribute("categorie", categorie.get());
				}
			}
			
			
			return "categorie/editCategorie";
		}
		
		@PostMapping("/edit-categorie")
		public String editCategorie(@Validated Categorie categorie, BindingResult bindingResult,  
				@RequestParam("id") Optional<Long> id)  {
			
			
			
			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.hasErrors());
				

				return "categorie/ajoutCategorie";
			}
			

			
			categorieServices.createCategorie(categorie);
			
			
			return "redirect:/liste-categories";
		}	
		
		//*************DELETE
		@GetMapping("/liste-categories/delete/{id}")
		public String deleteCategorie(@PathVariable(value="id") Long id, Model model) {
		
			Optional<Categorie> categorie =categorieServices.getById(id);
			
			List<Photo> photos = photoServices.getByCategorieId(id);
			
			if(photos.isEmpty() && categorie.isPresent()) {
				
				categorieServices.deleteCategorie(categorie.get());
			}
			return "redirect:/liste-categories";
		}
}
