package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Photographe {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nom; 
	
	@NotNull
	private String prenom;
	
	@Email(message = "Adresse email non valide")
	@NotEmpty(message = "Adresse email obligatoire")
	@Column(name="email", length=255, nullable=false, unique=true)
	private String email;
	
	private String password;
	
	private float latDefault;
	
	private float longDefault;
	
	private boolean role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getLatDefault() {
		return latDefault;
	}

	public void setLatDefault(float latDefault) {
		this.latDefault = latDefault;
	}

	public float getLongDefault() {
		return longDefault;
	}

	public void setLongDefault(float longDefault) {
		this.longDefault = longDefault;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}
	
	
}
