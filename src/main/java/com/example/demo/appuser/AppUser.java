package com.example.demo.appuser;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.appuser.AppUser;
import com.example.demo.image.Image;
import com.example.demo.pet.Pet;

@Entity
@Table(name = "APPUSER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "follower", "like", "report"})
public class AppUser {

	@OneToMany
	@JoinColumn(name = "owner")
	// @JsonIgnore
	// private Set<Pet> petsOwned = new HashSet<>();
	//@OrderColumn(name="pet_index")
	//@JsonBackReference
	private Set<Pet> petsOwned = new HashSet<>();

	@ManyToMany(mappedBy = "report_by", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JsonIgnore
	//@JsonManagedReference(value = "user-report")
	private Set<Image> reportImage = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "like_by")
	//@JsonIgnore
	//@JsonManagedReference(value = "user-like")
	private Set<Image> likeImage = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "FOLLOW", joinColumns = {
			@JoinColumn(name = "follower_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "following_id", referencedColumnName = "id") })
	//@JsonIgnore
	//@JsonBackReference(value = "user-follow")
	private Set<AppUser> following = new HashSet<AppUser>();
	@ManyToMany(mappedBy = "following")
	//@JsonManagedReference(value = "user-follow")
	//@JsonIgnore
	private Set<AppUser> follower = new HashSet<AppUser>();

	public AppUser() {
		super();
	}

	public AppUser(String firstname, String lastname, String middleInit, String username, String pw, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.middleInit = middleInit;
		this.username = username;
		this.pw = pw;
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "firstname")
	@NotEmpty(message = "must give first name")
	@Size(min = 1, max = 20)
	private String firstname;

	@Column(name = "lastname")
	@NotEmpty(message = "must give last name")
	@Size(min = 1, max = 20)
	private String lastname;

	@Column(name = "middleInit")
	@Size(min = 0, max = 1, message = "give initial only")
	private String middleInit;

	@Column(name = "username", unique = true)
	@NotEmpty(message = "must give username")
	@Size(min = 5, max = 20, message = "username must be more than 6 and less than 20 characters")
	private String username;

	@Column(name = "pw")
	@NotEmpty(message = "must give password")
	@Size(min = 8, max = 20, message = "password must be more than 8 and less than 20 characters")
	private String pw;

	@Column(name = "email")
	@NotEmpty(message = "must give email")
	@Email
	private String email;

	@Column(name = "privilege", columnDefinition = "integer default 0")
	private Integer privilege = new Integer(0); // 0 = app user, 1 = moderator/admin

	@Column(name = "applyMod", columnDefinition = "integer default 0")
	private Integer applyMod = new Integer(0); // 0 = has not applied for Mod privilege, 1 = has applied for Mod
												// privilege

	//@CreationTimestamp
	//@Column(name = "joined")
	//@Temporal(TemporalType.TIMESTAMP)
	//private java.util.Date joined;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public java.util.Date getJoined() {
//		return joined;
//	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddleInit() {
		return middleInit;
	}

	public void setMiddleInit(String middleInit) {
		this.middleInit = middleInit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPrivilege(Integer privilege) {
		this.privilege = privilege;
	}

	public Integer getPrivilege() {
		return privilege;
	}

	public void setApplyMod(Integer applyMod) {
		this.applyMod = applyMod;
	}

	public Integer getApplymod() {
		return applyMod;
	}

	public void addFollower(AppUser follower) {
		this.follower.add(follower);
	}

	public Set<AppUser> getFollower() {
		return follower;
	}

	public void removeFollower(AppUser follower) {
		this.follower.remove(follower);
	}

	public void addFollowing(AppUser following) {
		this.following.add(following);
	}

	public void removeFollowing(AppUser following) {
		this.following.remove(following);
	}

	public Set<AppUser> getFollowing() {
		return following;
	}

	public Set<Image> getLike() {
		return likeImage;
	}

	public void addLike(Image likeImage) {
		this.likeImage.add(likeImage);
	}

	public void removeLike(Image image) {
		this.likeImage.remove(image);
	}

	public Set<Image> getReport() {
		return reportImage;
	}

	public void addReport(Image reportImage) {
		this.reportImage.add(reportImage);
	}

	public void removeReport(Image image) {
		this.likeImage.remove(image);
	}

	public Set<Pet> getPet() {
		return petsOwned;
	}

	public void addPet(Pet pet) {
		this.petsOwned.add(pet);
	}

	public void removePet(Pet pet) {
		this.petsOwned.remove(pet);
	}
}
