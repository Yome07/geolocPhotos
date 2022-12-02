	package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Comparator;
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
import com.example.demo.model.Category;
import com.example.demo.model.Photo;
import com.example.demo.model.User;
import com.example.demo.model.UserLogin;
import com.example.demo.service.AlbumServices;
import com.example.demo.service.CategoryServices;
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
	private CategoryServices categoryServices;
	
	
	
	@GetMapping("/add-photo")
	public String addPhoto(Model model) {
		
		String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

		if (username != null) {
			User user = new User();
			user = userServices.findByEmail(username);
			
			List<Album> myAlbums = albumServices.getAlbumsByUserId(user.getId());
			model.addAttribute("myAlbums", myAlbums);
		}
		
		List<Category> categories = categoryServices.findAll();

		model.addAttribute("categories", categories);
		
		return "photo/addPhoto";
	}
	
	@PostMapping("/add-photo")
	public String addPhoto(@Validated Photo photo, BindingResult bindingResult,  
			@RequestParam("id") Optional<Long> id,
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		photo.setFileName(fileName);
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			System.out.println(bindingResult.getFieldError());
			return "photo/addPhoto";
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
			
			return "redirect:/my-photos";
		}
		
		return "home";
	}
	
	//Liste des photos
	@GetMapping("/list-photos")
	public String listPhotos(Model model) {
		List<Photo> photos = photoServices.findAll();
		
		model.addAttribute("photos", photos);
		return "photo/listPhotos";
	}
	
	// affichage de mes photos
		@GetMapping("/my-photos")
		public String mesPhotos(Photo photo, @RequestParam(value = "id", required = false) Long id, Model model) {
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			User user = new User();
			user = userServices.findByEmail(username);

			List<Photo> myPhotos = photoServices.getByUserId(user.getId());

			model.addAttribute("myPhotos", myPhotos);
			
			return "photo/myPhotos";
			
		}
		
		// affichage de mes photos en liste
				@GetMapping("/my-photos-list")
				public String myPhotosList(Photo photo, @RequestParam(value = "id", required = false) Long id, Model model) {
					String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

					User user = new User();
					user = userServices.findByEmail(username);

					List<Photo> photos = photoServices.getByUserId(user.getId());

					model.addAttribute("photos", photos);
					
					return "photo/listPhotos";
					
				}
		
	
		
		//************EDIT
		@GetMapping("/edit-photo/{id}")
		public String editPhoto(Model model, @PathVariable(value="id") Long id) {
			
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			if (username != null) {
				User user = new User();
				user = userServices.findByEmail(username);
				
				List<Album> myAlbums = albumServices.getAlbumsByUserId(user.getId());
				model.addAttribute("myAlbums", myAlbums);
			}
			
			List<Category> categories = categoryServices.findAll();

			model.addAttribute("categories", categories);
			
			if(id != null) {
				Optional<Photo> photo = photoServices.getById(id);
				if(photo.isPresent()) {
					model.addAttribute("photo", photo.get());
				}
			}
			
			
			return "photo/editPhoto";
		}
		
		@PostMapping("/edit-photo/{id}")
		public String editPhoto(@Validated Photo photo, BindingResult bindingResult,
								@PathVariable("id") long id)  {
			
			
			
			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.hasErrors());
				photo.setId(id);
				return "photo/editPhoto";
			}
			
			
			String username = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

			if (username != null) {
				User user = new User();
				user = userServices.findByEmail(username);
				
				photo.setUser(user);
				
				photoServices.createPhoto(photo);
				
				
				return "redirect:/my-photos";
				
			}
			
			return "home";
		}
		
		
		//*************DELETE
		@GetMapping("/my-photos/delete/{id}")
		public String deletePhoto(@PathVariable(value="id") Long id, Model model) throws IOException {
		
			Optional<Photo> photo =photoServices.getById(id);
			Path path = Paths.get("src/main/resources/static/photos-files/" + id + "/");

			if((photo).isPresent()) {
				
				photoServices.deletePhoto(photo.get());
				try {
					Files.walk(path)
							.sorted(Comparator.reverseOrder())
							.map(Path::toFile)
							.forEach(File::delete);
					System.out.println("File or directory deleted successfully");
				} catch (NoSuchFileException ex) {
					System.out.printf("No such file or directory: %s\n", path);
				} catch (IOException ex) {
					System.out.println(ex);
				}
			}
			return "redirect:/my-photos";
		}
}
