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
	public String ajoutPhoto(Model model) {
		
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
	public String ajoutPhoto(@Validated Photo photo, BindingResult bindingResult,  
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
	@GetMapping("/listPhotos")
	public String listPhotos(Model model) {
		List<Photo> photos = photoServices.findAll();
		
		model.addAttribute("photos", photos);
		
		return "photo/listPhoto";
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
		
		//************EDIT
		@GetMapping("/edit-photo")
		public String accueil(Photo photo,  @RequestParam(value = "id", required = false) Long id,Model model) {
			
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
				Optional<Photo> picture = photoServices.getById(id);
				if(picture.isPresent()) {
					model.addAttribute("photo", picture.get());
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
				
				Photo savedPhoto = photoServices.createPhoto(photo);
				
				
				
				
				System.out.println("file : " + photo.getFileName());
				System.out.println("titre : " + photo.getTitre());
				System.out.println("date : " + photo.getDate());
				System.out.println("description : " + photo.getDescription());
				System.out.println("lat : " + photo.getLatitude());
				System.out.println("long : " + photo.getLongitude());
				System.out.println("publique : " + photo.isPublique());
				//System.out.println("album : " + photo.getAlbum().getNom());
				return "redirect:/mes-photos";
				
			}
			
			return "home";
		}
		
		
		//*************DELETE
		@GetMapping("/mes-photos/delete/{id}")
		public String supprimer(@PathVariable(value="id") Long id, Model model) {
		
			Optional<Photo> photo =photoServices.getById(id);
			
			if((photo).isPresent()) {
				
				photoServices.deletePhoto(photo.get());
			}
			return "redirect:/mes-photos";
		}
}
