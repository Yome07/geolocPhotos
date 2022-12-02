package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserServices;

@Controller
public class RegisterController {
	
	@Autowired
	private UserServices userServices;
	
	
	@GetMapping("/signup")
	public String register(User user) {
		return "user/add";
	}

	@PostMapping("/signup")
	public String addUser(@Validated User user, BindingResult bindingResult) {
		
		//model.addAttribute("user", new User());
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			System.out.println(bindingResult.getFieldError());

			return "user/add";
		}
		
		if(userServices.findByEmail(user.getEmail()) != null) {
	    	bindingResult.addError(new FieldError("friend","email","Cette adresse email existe déjà !"));
			return ("user/add");

	    }
	
		userServices.createUser(user);
		return "redirect:/login";
	}
}
