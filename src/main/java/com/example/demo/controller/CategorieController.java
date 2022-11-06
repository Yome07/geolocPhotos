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

import com.example.demo.model.Categorie;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CategorieServices;

@Controller
public class CategorieController {

	@Autowired
	private CategorieServices categorieServices;
	
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
}
