package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findByEmail(String email) {
        return  userRepository.findByEmail(email);
    }
	
	public void createUser(User user) {
		
		BCryptPasswordEncoder encodePwd = new BCryptPasswordEncoder();
		user.setPassword(encodePwd.encode(user.getPassword()));
		
		userRepository.save(user);
	}

}
