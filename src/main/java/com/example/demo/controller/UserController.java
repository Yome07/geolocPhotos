package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.service.UserServices;

@Controller
public class UserController {

	@Autowired
	private UserServices userServices;
	
	//Liste des photographes
	@GetMapping("/list-users")
	public String listPhotographer(Model model) {
		List<User> users = userServices.findAll();
		
		model.addAttribute("users", users);
		
		return "user/list";
	}
	
	//profil utilisateur
	@GetMapping("/edit-user")
	public String showUser(Model model ) {
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		User user = userServices.findByEmail(username);
		model.addAttribute("user", user);
		user.setOldEmail(username);
		System.out.println(user);
		System.out.println(user.getEmail() == user.getOldEmail());
		return "user/edit";
	}
	
	@PostMapping("/edit-user")
	public String editUser(@Validated User user, BindingResult bindingResult, @RequestParam("id") Optional<Long> id) {
		
		
		
		
		if(user.getEmail() != user.getOldEmail()) {
			if(userServices.findByEmail(user.getEmail()) != null) {
		    	bindingResult.addError(new FieldError("friend","email","Cette adresse email existe déjà !"));
		    	return ("user/edit");
		    }
		}
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return "user/edit";
		}
		
	    userServices.createUser(user);
	    System.out.println(user.getOldEmail());
	    System.out.println(user);
		return "redirect:/";
	}
}
