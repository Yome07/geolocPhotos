package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Champ obligatoire")
	private String name;
	
	@NotNull(message = "Champ obligatoire")
	private String firstname;
	
	@Email(message = "Adresse email non valide")
	@NotEmpty(message = "Adresse email obligatoire")
	@Column(name="email", length=255, nullable=false, unique=true)
	private String email;
	
	@Transient
	private String oldEmail;

	@NotNull(message = "le mot de passe ne peut être vide")
	@Pattern(regexp = "^([a-zA-Z_0-9!@#$&*]){8,100}$",
	message = "Le mot de passe doit contenir des minuscules, majuscules, des chiffres et des caractères spéciaux parmi !@#$&*")
	@Length(min = 8, max = 100)
	private String password;
	
	private float latDefault;
	
	private float longDefault;
	
	private boolean role;


	public User() {
	}

	public User(String name, String firstname, String email, String password, float latDefault, float longDefault, boolean role) {
		this.name = name;
		this.firstname = firstname;
		this.email = email;
		this.password = password;
		this.latDefault = latDefault;
		this.longDefault = longDefault;
		this.role = role;
	}

	public User(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getOldEmail() {
		return oldEmail;
	}
	
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstname=" + firstname + ", email=" + email + ", oldEmail="
				+ oldEmail + ", password=" + password + ", latDefault=" + latDefault + ", longDefault=" + longDefault
				+ ", role=" + role + "]";
	}
	
	
}
