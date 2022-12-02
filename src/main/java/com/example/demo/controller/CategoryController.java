package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;
import com.example.demo.service.UserServices;
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

import com.example.demo.model.Category;
import com.example.demo.model.Photo;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryServices;
import com.example.demo.service.PhotoServices;

@Controller
public class CategoryController {

	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private PhotoServices photoServices;

	@Autowired
	private UserServices userServices;
	

	@Autowired CategoryRepository categoryRepository;
	
	@GetMapping("/add-category")
	public String addCategory() {
		return "/category/form";
	}
	
	@PostMapping("/add-category")
	public String addCategory(@Validated Category category, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return "album/form";
		}
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		if (username != null) {
			
			categoryServices.createCategory(category);
			
			return "redirect:/";
		}
		
		return "category/add";
	}
	
	// liste de toutes les catégories
	@GetMapping("/list-categories")
	public String list(Model model) {
		
		
		List<Category> categories = categoryServices.findAll();
		
		model.addAttribute("categories", categories);
		
		return "category/list";
		
	}
	
	// liste des photos par catégorie
	@GetMapping("/list-photos-by-category/{id}")
	public String listPhotosByCategory(Model model, @PathVariable(value="id") Long id) {
		List<Photo> photos = photoServices.getByCategoryId(id);
		
		model.addAttribute("photos", photos);
		System.out.println(photos);
		
		return "category/listPhotos";
	}
	
	//************EDIT
		@GetMapping("/edit-category/{id}")
		public String editCategory(Model model, @PathVariable(value="id") Long id) {
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			if (username != null) {
				User user = new User();
				user = userServices.findByEmail(username);
			}

			if(id != null) {
				Optional<Category> category = categoryServices.getById(id);
				if(category.isPresent()) {
					model.addAttribute("category", category.get());
				}
			}
			
			
			return "category/editCategory";
		}
		
		@PostMapping("/edit-category/{id}")
		public String editCategory(@Validated Category category, BindingResult bindingResult,
								   @PathVariable("id") long id)  {

			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.hasErrors());
				category.setId(id);
				return "category/addCategory";
			}

			categoryServices.createCategory(category);

			return "redirect:/list-categories";
		}	
		
		//*************DELETE
		@GetMapping("/list-categories/delete/{id}")
		public String deleteCategory(@PathVariable(value="id") Long id, Model model) {
		
			Optional<Category> category =categoryServices.getById(id);
			
			List<Photo> photos = photoServices.getByCategoryId(id);
			
			if(photos.isEmpty() && category.isPresent()) {
				
				categoryServices.deleteCategory(category.get());
			}
			return "redirect:/list-categories";
		}
}
