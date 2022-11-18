package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Category {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "category")
	private Set<Photo> photo = new HashSet<Photo>();

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

	public Set<Photo> getPhoto() {
		return photo;
	}

	public void setPhoto(Set<Photo> photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
}
