package com.example.demo.page;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.image.Image;
import com.example.demo.image.ImageRepository;
import com.example.demo.pet.Pet;
//import com.example.demo.appuser.AppUserRepository;
import com.example.demo.pet.PetRepository;

@RestController
public class PageController {

	@Autowired
	PetRepository petRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	PageRepository pageRepository;

	private String success = "{\"message\":\"success\"}";
	private String failure = "{\"message\":\"failure\"}";

	@GetMapping(path = "/page")
	List<Page> getAllPages() {
		return pageRepository.findAll();
	}

	@GetMapping(path = "/page/id/{id}")
	Page getPageById(@PathVariable("id") int id) {
		return pageRepository.findById(id);

	}

	@GetMapping(path = "/page/name/{name}")
	List<Page> getPageByName(@PathVariable("name") String name) {
		List<Page> pageResult = pageRepository.findByName(name);
		return pageResult;

	}
	
	
	@PostMapping(path = "/page")
	String createPage(@RequestBody Page pg) {
		if (pg == null)
			return failure;
		pageRepository.save(pg);
		return success;
	}

	@DeleteMapping(path = "/page/id/{id}")
	String deleteUser(@PathVariable("id") int id) {
		pageRepository.deleteById(id);
		return success;
	}

//---------------------------------------------------------------\\
//																 \\

//	@GetMapping(path = "/page/id/{id}/image")
//	private Set<Image> getImages(@PathVariable("id") int id) {
//		Page pg = pageRepository.findById(id);
//		return pg.getImage();
//	}

	/*
	@GetMapping(path = "/page/id/{id}/image")
	public Set<Image> getImageByPage(@PathVariable("id") int id){
		Page pg = pageRepository.findById(id);
		return pg.getImage();
	}
	*/

	
	@PutMapping(path = "/page/id/{id}")
	public ResponseEntity<String> editPage(@PathVariable("id") int id, @RequestBody Page edit) {
		Page pg = pageRepository.findById(id);
		if (pg == null) {
			return new ResponseEntity<>("Page not found", HttpStatus.NOT_FOUND);
		}
		pg.setPageName(edit.getPageName());
		pageRepository.save(pg);
		return new ResponseEntity<>("Page name updated successfully", HttpStatus.OK);
	}
	
	@GetMapping(path = "/page/id/{id}/pet")
	public Pet getPetByPage(@PathVariable("id") int id) {
		return pageRepository.findById(id).getPet();
	}
}
