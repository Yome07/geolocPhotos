package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserServices;

@Controller
public class UserController {

	@Autowired
	private UserServices userServices;
	
	//Liste des photographes
	@GetMapping("/liste-users")
	public String listPhotogrpahes(Model model) {
		List<User> users = userServices.findAll();
		
		model.addAttribute("users", users);
		
		return "user/list";
	}
}
