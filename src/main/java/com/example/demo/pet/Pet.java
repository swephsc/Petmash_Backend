package com.example.demo.pet;

import java.util.HashSet;
import java.util.Set;

//import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.demo.appuser.AppUser;
import com.example.demo.page.Page;
import com.example.demo.pet.Pet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "PET")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	@NotEmpty(message = "must give pet name")
	@Size(min = 1, max = 50, message = "pet name must be less than 50 characters")
	private String name;

	@Column(name = "type")
	@Size(min = 1, max = 50, message = "pet type must be less than 50 characters")
	private String type;

	@Column(name = "info")
	@Size(min = 1, max = 500, message = "pet info must be less than 500 characters")
	private String info;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "page")
	//@JsonBackReference(value = "pet-page")
	private Page pg;

	public Pet() {
		super();
	}

	public Pet(String name, String type, String info) {
		super();
		this.name = name;
		this.type = type;
		this.info = info;
	}

	public String getPetInfo() {
		return info;
	}

	public void setPetInfo(String info) {
		this.info = info;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPetName() {
		return name;
	}

	public void setPetName(String name) {
		this.name = name;
	}

	public String getPetType() {
		return type;
	}

	public void setPetType(String type) {
		this.type = type;
	}

	public Page getPg() {
		return pg;
	}

	public void setPg(Page pg) {
		this.pg = pg;
	}

	// public AppUser getOwner() {
	// return this.getOwner();
	// }

	// public void setOwner(AppUser owner) {
	// this.setOwner(owner);
	// }

	// public List<Image> getImage(){
	// return this.set
	// }
}
