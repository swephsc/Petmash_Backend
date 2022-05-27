package com.example.demo.page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
//import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;

import com.example.demo.image.Image;
import com.example.demo.pet.Pet;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

//import com.example.demo.image.Image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "PAGE")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pet", "imageUploaded"})
public class Page {

	@OneToMany(mappedBy = "page", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @JsonIgnore
	@JsonManagedReference(value = "page-image")
	private Set<Image> imageUploaded = new HashSet<>();

	@OneToOne(mappedBy = "pg", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JsonIgnore
	//@JsonManagedReference(value = "pet-page")
	private Pet pet;

	public Page() {
		super();
	}

	public Page(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Digits(fraction = 0, integer = 4)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "name", unique = true)
	@NotEmpty(message = "must give page name")
	@Size(min = 1, max = 100, message = "name must be less than 100 characters")
	private String name;

	public int getPageId() {
		return id;
	}

	public void setPageId(int id) {
		this.id = id;
	}

	public String getPageName() {
		return name;
	}

	public void setPageName(String name) {
		this.name = name;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Set<Image> getImage() {
		return imageUploaded;
	}

	public void addImage(Image image) {
		this.imageUploaded.add(image);
	}

	public void deleteImage(Image image) {
		this.imageUploaded.remove(image);
	}
}
