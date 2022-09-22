	package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.repository.PhotoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PhotoServices;

@Controller
public class PhotoController {

	@Autowired
	private PhotoServices photoServices;
	
	@Autowired UserRepository userRepository;
	@Autowired PhotoRepository photoRepository;
	
	@GetMapping("/ajout-photo")
	public String ajoutPhoto() {
		
		return "photo/ajoutPhoto";
	}
	
	@PostMapping("/ajout-photo")
	public String ajoutPhoto(@Validated Photo photo, BindingResult bindingResult,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		photo.setFileName(fileName);
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			System.out.println(bindingResult.getObjectName());
			System.out.println("file : " + photo.getFileName());
			System.out.println("titre : " + photo.getTitre());
			System.out.println("date : " + photo.getDate());
			System.out.println("description : " + photo.getDescription());
			System.out.println("lat : " + photo.getLatitude());
			System.out.println("long : " + photo.getLongitude());
			System.out.println("publique : " + photo.isPublique());
			System.out.println("____________");
			System.out.println(bindingResult.getFieldError());

			return "/photo/ajoutPhoto";
		}
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userRepository.findByEmail(username);
			
			photo.setUser(user);
			
			Photo savedPhoto = photoServices.createPhoto(photo);

			String uploadDir = "src/main/resources/static/photos-files/" + savedPhoto.getId();
			
			Path uploadPath = Paths.get(uploadDir);
			
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new IOException("Impossible de sauvegarder le fichier : " + fileName, e);
			}
			return "redirect:/";
			
		}
		
		return "photo/ajoutPhoto";
	}
	
	//Liste des photos
	@GetMapping("/listPhotos")
	public String listPhotos(Model model) {
		List<Photo> photos = photoServices.findAll();
		
		model.addAttribute("photos", photos);
		
		return "photo/listPhoto";
	}
}
