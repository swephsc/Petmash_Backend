package com.example.demo.image;

import java.util.ArrayList;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
//import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.appuser.AppUser;
import com.example.demo.image.Image;
import com.example.demo.page.Page;
import com.example.demo.pet.Pet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//import com.example.demo.page.Page;
//import javax.persistence.ManyToOne;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.hibernate.annotations.OnDelete; 
//import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "IMAGE")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
public class Image {
	
	private static String directory = "/uploadedFiles/";

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT", joinColumns = { @JoinColumn(name = "image_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") })
	//@JsonIgnore
	//@JsonBackReference(value = "user-report")
	private List<AppUser> report_by = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "LIKE_HISTORY", joinColumns = { @JoinColumn(name = "image_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") })
	//@JsonIgnore
	//@JsonBackReference(value = "user-like")
	private List<AppUser> like_by = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "page")
	@JsonBackReference(value = "page-image")
	private Page page;
	

//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH,
//			CascadeType.REMOVE })
//	@JoinColumn(name = "posted_on", referencedColumnName = "id")
//	@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore
//	private Page image_posted = new Page();;

	public Image() {
		super();
	}

	public Image(String name, String info) {
		super();
		this.name = name;
		this.info = info;
		this.path = "";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "like_num", columnDefinition = "integer default 0")
	private Integer like_num = new Integer(0);
	
	@Column(name = "name")
	@NotEmpty(message = "must give image name")
	@Size(min = 1, max = 50, message = "50 characters max")
	private String name;

	@Column(name = "info")
	@Size(min = 1, max = 250, message = "describe image in 250 chracters or less")
	private String info;

	@Column(name = "path")//took out : unique = true
	private String path;
	
	@Column(name = "reported", columnDefinition = "integer default 0")
	private Integer report_num  = new Integer(0);;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPath() {
		return path;
	}
	
	public void setRandomPath() {
		Random rand = new Random();
		int i = rand.nextInt(10);
		String newPath = directory + i + ".jpeg";
		this.path = newPath;
	}
	
	public void setPath(String path){
		this.path = path;
	}

	public String getLike() {
		return like_num.toString();
	}

	public void setLike() {
		this.like_num++;
	}
	
	public void deleteLike() {
		this.like_num--;
	}

	public String getReport() {
		return report_num.toString();
	}

	public void setReport() {
		this.report_num++;
	}
	
	public void deleteReport() {
		this.report_num--;
	}
//	public Page getPage() {
//		return image_posted;
//	}

//	public void setPage(Page image_posted) {
//		this.image_posted = image_posted;
//	}

	public List<AppUser> getLikeBy() {
		return like_by;
	}

//	public void setLikeBy(List<AppUser> like_by) {
//		this.like_by = like_by;
//	}

	public void addLikeBy(AppUser like_by) {
		this.like_by.add(like_by);
	}
	
	public void removeLikedBy(AppUser like_by) {
		this.like_by.remove(like_by);
	}

	public List<AppUser> getReportBy() {
		return report_by;
	}

//	public void setReportBy(List<AppUser> report_by) {
//		this.report_by = report_by;
//	}

	public void addReportBy(AppUser report_by) {
		this.report_by.add(report_by);
	}
	
	public void removeReportBy(AppUser report_by) {
		this.report_by.remove(report_by);
	}
	
	public Page getPage() {
		return page;
	}
	
	public void setPage(Page pg) {
		this.page = pg;
	}
}
