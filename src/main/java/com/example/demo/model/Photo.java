package com.example.demo.model;



import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Photo {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private String fileName;
	
	private Date date;
	
	private boolean publique;
	
	private float latitude;
	
	private float longitude;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="album_id")
	private Album album;

	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "photo_category",
			joinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
			)
	private Set<Category> category = new HashSet<Category>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "photo_keyword",
			joinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "keyword_id", referencedColumnName = "id")
			)
	private Set<Keyword> keyword = new HashSet<Keyword>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isPublique() {
		return publique;
	}

	public void setPublique(boolean publique) {
		this.publique = publique;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

	@Transient
	public String getFileNameImagePath() {
		if (fileName == null || id == null) return null;
		
		return "/photos-files/" + id + "/" + fileName;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", title=" + title + ", description=" + description + ", fileName=" + fileName
				+ ", date=" + date + ", publique=" + publique + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", user=" + user + ", album=" + album + ", category=" + category + "]";
	}
	
	
}
