package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserServices;

@Controller
public class RegisterController {
	
	@Autowired
	private UserServices userServices;
	
	@GetMapping("/inscription")
	public String register(User user) {
		return "user/ajout";
	}

	@PostMapping("/inscription")
	public String ajoutUser(@Validated User user, BindingResult bindingResult, Model model) {
		
		model.addAttribute("user", new User());
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return "user/ajout";
		}
		
		userServices.createUser(user);
		
		return "redirect:/";
	}
}
