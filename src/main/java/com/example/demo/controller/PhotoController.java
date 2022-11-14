	package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Album;
import com.example.demo.model.Categorie;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.service.AlbumServices;
import com.example.demo.service.CategorieServices;
import com.example.demo.service.PhotoServices;
import com.example.demo.service.UserServices;

@Controller
public class PhotoController {

	@Autowired
	private UserServices userServices;
	
	@Autowired
	private PhotoServices photoServices;
	
	@Autowired
	private AlbumServices albumServices;
	
	@Autowired
	private CategorieServices categorieServices;
	
	
	
	@GetMapping("/ajout-photo")
	public String addPhoto(Model model) {
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userServices.findByEmail(username);
			
			List<Album> mesAlbums = albumServices.getAlbumsByUserId(user.getId());
			model.addAttribute("mesAlbums", mesAlbums);
		}
		
		List<Categorie> categories = categorieServices.findAll();

		model.addAttribute("categories", categories);
		
		return "photo/ajoutPhoto";
	}
	
	@PostMapping("/ajout-photo")
	public String addPhoto(@Validated Photo photo, BindingResult bindingResult,  
			@RequestParam("id") Optional<Long> id,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		photo.setFileName(fileName);
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			System.out.println(bindingResult.getFieldError());
			return "/photo/ajoutPhoto";
		}
				
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userServices.findByEmail(username);
			
			photo.setUser(user);
			
			Photo savedPhoto;
			
			savedPhoto = photoServices.createPhoto(photo);
			
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
			
			return "redirect:/mes-photos";
		}
		
		return "home";
	}
	
	//Liste des photos
	@GetMapping("/liste-photos")
	public String listPhotos(Model model) {
		List<Photo> photos = photoServices.findAll();
		
		model.addAttribute("photos", photos);
		return "photo/listPhotos";
	}
	
	// affichage de mes photos
		@GetMapping("/mes-photos")
		public String mesPhotos(Photo photo, @RequestParam(value = "id", required = false) Long id, Model model) {
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			User user = new User();
			user = userServices.findByEmail(username);

			List<Photo> mesPhotos = photoServices.getByUserId(user.getId());

			model.addAttribute("mesPhotos", mesPhotos);
			
			return "photo/mesPhotos";
			
		}
		
		// affichage de mes photos en liste
				@GetMapping("/mes-photos-liste")
				public String mesPhotosListe(Photo photo, @RequestParam(value = "id", required = false) Long id, Model model) {
					String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

					User user = new User();
					user = userServices.findByEmail(username);

					List<Photo> photos = photoServices.getByUserId(user.getId());

					model.addAttribute("photos", photos);
					
					return "photo/listPhotos";
					
				}
		
	
		
		//************EDIT
		@GetMapping("/edit-photo")
		public String editPhoto(@RequestParam(value = "id", required = false) Long id,Model model) {
			
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			if (username != null) {
				User user = new User();
				user = userServices.findByEmail(username);
				
				List<Album> mesAlbums = albumServices.getAlbumsByUserId(user.getId());
				model.addAttribute("mesAlbums", mesAlbums);
			}
			
			List<Categorie> categories = categorieServices.findAll();

			model.addAttribute("categories", categories);
			
			if(id != null) {
				Optional<Photo> photo = photoServices.getById(id);
				if(photo.isPresent()) {
					model.addAttribute("photo", photo.get());
				}
			}
			
			
			return "photo/editPhoto";
		}
		
		@PostMapping("/edit-photo")
		public String editPhoto(@Validated Photo photo, BindingResult bindingResult,  
				@RequestParam("id") Optional<Long> id)  {
			
			
			
			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.hasErrors());
				

				return "/photo/ajoutPhoto";
			}
			
			
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			if (username != null) {
				User user = new User();
				user = userServices.findByEmail(username);
				
				photo.setUser(user);
				
				photoServices.createPhoto(photo);
				
				
				return "redirect:/mes-photos";
				
			}
			
			return "home";
		}
		
		
		//*************DELETE
		@GetMapping("/mes-photos/delete/{id}")
		public String deletePhoto(@PathVariable(value="id") Long id, Model model) {
		
			Optional<Photo> photo =photoServices.getById(id);
			
			if((photo).isPresent()) {
				
				photoServices.deletePhoto(photo.get());
			}
			return "redirect:/mes-photos";
		}
}
