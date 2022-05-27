package com.example.demo.pet;

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

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRepository;
import com.example.demo.image.Image;
import com.example.demo.image.ImageRepository;
import com.example.demo.page.Page;
//import com.example.demo.appuser.AppUser;
//import com.example.demo.appuser.AppUserRepository;
import com.example.demo.page.PageRepository;

@RestController
public class PetController {

	@Autowired
	PetRepository petRepository;
	
	@Autowired 
	AppUserRepository appuserRepository;
	
	@Autowired
	PageRepository pageRepository;
	
	@Autowired
	ImageRepository imageRepository;

	private String success = "{\"message\":\"success\"}";
	private String failure = "{\"message\":\"failure\"}";

	@GetMapping(path = "/pet")
	List<Pet> getAllPets() {
		return petRepository.findAll();
	}

	@GetMapping(path = "/pet/id/{id}")
	Pet getPetById(@PathVariable("id") int id) {
		return petRepository.findById(id);
	}

	@GetMapping(path = "/pet/name/{name}")
	List<Pet> getPetByName(@PathVariable("name") String name) {
		List<Pet> petResult = petRepository.findByName(name);
		return petResult;

	}

	@GetMapping(path = "/pet/type/{type}")
	List<Pet> getPetByType(@PathVariable("type") String type) {
		List<Pet> petResult = petRepository.findByType(type);
		return petResult;
	}

	@GetMapping(path = "/pet/owner/id/{id}")
	public List<Pet> getPetByOwner(@PathVariable("id") int id) {
		return petRepository.findByOwner(id);
	}
	
	@GetMapping(path = "/pet/owner/username/{username}")
	public List<Pet> getPetByOwnerUsername(@PathVariable("username") String username) {
		AppUser user = appuserRepository.findByUsername(username);
		int id = user.getId();
		return petRepository.findByOwner(id);
	}
	
	@GetMapping(path = "/pet/id/{id}/image")
	public Set<Image> getAllImage(@PathVariable("id") int id){
		Pet pet = petRepository.findById(id);
		Page page = pageRepository.findById(pet.getPg().getPageId());
			
		return page.getImage();
	}

	@GetMapping(path = "/pet/rand")
	Pet getPetRand() {
		Pet petResult = petRepository.findPetRandom();
		return petResult;
	}
	
	@PostMapping(path = "/pet")
	Pet createPet(@RequestBody Pet pet) {
		if (pet == null) {
			return null;
		} else {
			petRepository.save(pet);
			return petRepository.findById(pet.getId());
		}
	}

	@PutMapping(path = "/pet/id/{id}")
	public ResponseEntity<String> updatePet(@PathVariable("id") int id, @RequestBody Pet request) {
		Pet pet = petRepository.findById(id);
		if (pet == null) {
			return new ResponseEntity<>("Pet now found", HttpStatus.NOT_FOUND);
		}
		if(request.getPetInfo() != null) {
			pet.setPetInfo(request.getPetInfo());
		}
		if(request.getPetName() != null) {
			pet.setPetName(request.getPetName());
		}
		if(request.getPetType() != null) {
			pet.setPetType(request.getPetType());
		}
		petRepository.save(pet);
		return new ResponseEntity<>("Pet info update successful", HttpStatus.OK);
	}
	
	@PutMapping(path = "/pet/id/{id}/owner/{username}")
	public ResponseEntity<String> updatePetOwner(@PathVariable("id") int id, @PathVariable("username") String username){
		Pet pet = petRepository.findById(id);
		AppUser owner = appuserRepository.findByUsername(username);
		if(pet == null) {
			return new ResponseEntity<>("Pet not found", HttpStatus.NOT_FOUND);
		}
		if(owner == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		owner.addPet(petRepository.findById(id));
		appuserRepository.save(owner);
		return new ResponseEntity<>("Pet owner added successfully", HttpStatus.OK);
	}
	
	@PutMapping(path = "/pet/id/{id}/page/{page}")
	public ResponseEntity<String> updatePetPage(@PathVariable("id") int id, @PathVariable("page") int page){
		Pet pet = petRepository.findById(id);
		Page petPage = pageRepository.findById(page);
		if(pet == null) {
			return new ResponseEntity<>("Pet not found", HttpStatus.NOT_FOUND);
		}
		if(petPage == null) {
			return new ResponseEntity<>("Page not found", HttpStatus.NOT_FOUND);
		}
		
		pet.setPg(petPage);
		petRepository.save(pet);
		return new ResponseEntity<>("Pet page added successfully", HttpStatus.OK);
	}

	@DeleteMapping(path = "/pet/id/{id}")
	String deletePet(@PathVariable("id") int id) {
		petRepository.deleteById(id);
		return success;
	}
}
