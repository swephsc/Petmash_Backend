package com.example.demo.appuser;

import java.util.List;
import java.util.Set;

//import org.springframework.beans.BeanUtils;
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
//import com.example.demo.page.PageRepository;
//import com.example.demo.pet.PetRepository;
import com.example.demo.pet.Pet;
import com.example.demo.pet.PetRepository;

@RestController
public class AppUserController {

	@Autowired
	PetRepository petRepository;

	@Autowired
	AppUserRepository appuserRepository;

//	@Autowired
//	PageRepository pageRepository;

	@Autowired
	ImageRepository imageRepository;

	private String success = "{\"message\":\"success\"}";
	private String failure = "{\"message\":\"failure\"}";

	// private final Logger logger =
	// LoggerFactory.getLogger(AppUserController.class);

	@GetMapping(path = "/user")
	List<AppUser> getAllAppUsers() {
		return appuserRepository.findAll();
	}

	@GetMapping(path = "/user/id/{id}")
	AppUser getUserById(@PathVariable("id") int id) {
		return appuserRepository.findById(id);
	}

	@GetMapping(path = "/user/username/{username}")
	AppUser getUserByUsername(@PathVariable("username") String username) {
		return appuserRepository.findByUsername(username);
	}

	@GetMapping(path = "/user/firstname/{firstname}")
	List<AppUser> getUserByFirstname(@PathVariable("firstname") String firstname) {
		List<AppUser> userResult = appuserRepository.findByFirstname(firstname);
		return userResult;
	}

	@GetMapping(path = "/user/lastname/{lastname}")
	List<AppUser> getUserByLastname(@PathVariable("lastname") String lastname) {
		List<AppUser> userResult = appuserRepository.findByLastname(lastname);
		return userResult;
	}

	@GetMapping(path = "/user/email/{email}")
	List<AppUser> getUserByEmail(@PathVariable("email") String email) {
		List<AppUser> userResult = appuserRepository.findByEmail(email);
		return userResult;
	}

	@GetMapping(path = "/user/mod")
	List<AppUser> getModerator() {
		List<AppUser> userResult = appuserRepository.findMod();
		return userResult;
	}

	@GetMapping(path = "/user/applicant")
	List<AppUser> getApplicant() {
		List<AppUser> userResult = appuserRepository.findApplicant();
		return userResult;
	}

	@GetMapping(path = "/user/id/{id}/pet")
	Set<String> getPet(@PathVariable("id") int id) {
		Set<String> myPet = appuserRepository.findPet(id);
		return myPet;
	}

	@GetMapping(path = "/user/username/{username}/pet")
	Set<String> getPetByUsername(@PathVariable("username") String username) {
		AppUser user = appuserRepository.findByUsername(username);
		int id = user.getId();
		Set<String> myPet = appuserRepository.findPet(id);
		return myPet;
	}

	@PostMapping(path = "/user")
	String createUser(@RequestBody AppUser user) {
		if (user == null) {
			return failure;
		}
		appuserRepository.save(user);
		return success;
	}

	@GetMapping(path = "/user/id/{id}/follower")
	Set<String> getFollower(@PathVariable("id") int id) {
		Set<String> follower = appuserRepository.findFollower(id);
		return follower;
	}

	@GetMapping(path = "/user/id/{id}/following")
	Set<String> getFollowing(@PathVariable("id") int id) {
		Set<String> following = appuserRepository.findFollowing(id);
		return following;
	}

	@GetMapping(path = "/user/username/{username}/privilege")
	Integer isMod(@PathVariable("username") String username) {
		Integer user = appuserRepository.isMod(username);
		return user;
	} // returns only 0 or 1; Joe asked for this feature.

	// method for the user to follow other accounts
	@PutMapping(path = "/user/id/{id}/following/{following}")
	public ResponseEntity<String> editFollowing(@PathVariable("id") int id, @PathVariable("following") int following) {
		AppUser user = appuserRepository.findById(id);
		AppUser follow = appuserRepository.findById(following);
		if (user == null || follow == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		if (user.getFollowing().contains(follow)) { // un-follow
			user.removeFollowing(follow);
			follow.removeFollower(user);
			appuserRepository.save(user);
			appuserRepository.save(follow);
			return new ResponseEntity<>("Following removed successfully", HttpStatus.OK);
		} else {
			user.addFollowing(follow);
			follow.addFollower(user);
			appuserRepository.save(user);
			appuserRepository.save(follow);
			return new ResponseEntity<>("Following added successfully", HttpStatus.OK);
		}
	}

	@PutMapping(path = "/user/id/{id}")
	public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody AppUser request) {
		AppUser user = appuserRepository.findById(id);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (request.getEmail() != null) {
			user.setEmail(request.getEmail());
		}
		if (request.getPw() != null) {
			user.setPw(request.getPw());
		}
		if (request.getUsername() != null) {
			user.setUsername(request.getUsername());
		}
		appuserRepository.save(user);
		return new ResponseEntity<>("User info update successful", HttpStatus.OK);
	}

	@PutMapping(path = "/user/id/{id}/privilege/{privilege}")
	public ResponseEntity<String> modPrivilege(@PathVariable("id") int id, @PathVariable("privilege") int privilege) {
		AppUser user = appuserRepository.findById(id);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		user.setPrivilege(Integer.valueOf(privilege));
		user.setApplyMod(0);
		appuserRepository.save(user);
		return new ResponseEntity<>("User privilege changed successfully", HttpStatus.OK);
	}

	@PutMapping(path = "/user/id/{id}/applyMod/{applyMod}")
	public ResponseEntity<String> applyMod(@PathVariable("id") int id, @PathVariable("applyMod") int applyMod) {
		AppUser user = appuserRepository.findById(id);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		user.setApplyMod(Integer.valueOf(applyMod));
		appuserRepository.save(user);
		return new ResponseEntity<>("User applied for moderator", HttpStatus.OK);
	}

	@PutMapping(path = "/user/id/{id}/like/{image}")
	public ResponseEntity<String> likeImage(@PathVariable("id") int id, @PathVariable("image") int image) {
		AppUser user = appuserRepository.findById(id);
		Image file = imageRepository.findById(image);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (file == null) {
			return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
		}
		if (user.getLike().contains(file)) {
			return new ResponseEntity<>("Image liked by the user already", HttpStatus.FOUND);
		} else {
			user.addLike(file);
			file.addLikeBy(user);
			file.setLike();
			imageRepository.save(file);
			appuserRepository.save(user);
			return new ResponseEntity<>("Liked the image successfully", HttpStatus.OK);
		}
	}

	@PutMapping(path = "/user/username/{username}/like/{image}")
	public ResponseEntity<String> likeImageByUsername(@PathVariable("username") String username,
			@PathVariable("image") int image) {
		AppUser user = appuserRepository.findByUsername(username);
		Image file = imageRepository.findById(image);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (file == null) {
			return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
		}
		if (user.getLike().contains(file) && !username.equals("tester")) {
			return new ResponseEntity<>("Image liked by the user already", HttpStatus.FOUND);
		} else {
			user.addLike(file);
			file.addLikeBy(user);
			file.setLike();
			imageRepository.save(file);
			appuserRepository.save(user);
			return new ResponseEntity<>("Liked the image successfully", HttpStatus.OK);
		}
	}

	@PutMapping(path = "/user/id/{id}/report/{image}")
	public ResponseEntity<String> reportImage(@PathVariable("id") int id, @PathVariable("image") int image) {
		AppUser user = appuserRepository.findById(id);
		Image file = imageRepository.findById(image);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (file == null) {
			return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
		}
		if (user.getUsername().toString() == "test") {
			user.addReport(file);
			file.addReportBy(user);
			file.setReport();
			appuserRepository.save(user);
			imageRepository.save(file);
			return new ResponseEntity<>("TEST: image liked successfully", HttpStatus.OK);
		}
		if (user.getReport().contains(file)) {
			user.removeReport(file);
			file.removeReportBy(user);
			file.deleteReport();
			appuserRepository.save(user);
			imageRepository.save(file);
			return new ResponseEntity<>("Undo-reported the image successfully", HttpStatus.OK);
		} else {
			user.addReport(file);
			file.addReportBy(user);
			file.setReport();
			appuserRepository.save(user);
			imageRepository.save(file);
			return new ResponseEntity<>("Reported the image successfully", HttpStatus.OK);
		}
	}

	@PutMapping(path = "/user/username/{username}/report/{image}")
	public ResponseEntity<String> reportImageByUsername(@PathVariable("username") String username,
			@PathVariable("image") int image) {
		AppUser user = appuserRepository.findByUsername(username);
		Image file = imageRepository.findById(image);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (file == null) {
			return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
		}
		if (user.getUsername().toString() == "test") {
			user.addReport(file);
			file.addReportBy(user);
			file.setReport();
			appuserRepository.save(user);
			imageRepository.save(file);
			return new ResponseEntity<>("TEST: image liked successfully", HttpStatus.OK);
		}
		if (user.getReport().contains(file)) {
			user.removeReport(file);
			file.removeReportBy(user);
			file.deleteReport();
			appuserRepository.save(user);
			imageRepository.save(file);
			return new ResponseEntity<>("Undo-reported the image successfully", HttpStatus.OK);
		} else {
			user.addReport(file);
			file.addReportBy(user);
			file.setReport();
			appuserRepository.save(user);
			imageRepository.save(file);
			return new ResponseEntity<>("Reported the image successfully", HttpStatus.OK);
		}
	}

	@PutMapping(path = "/user/id/{id}/pet/{pet}")
	public ResponseEntity<String> registerPet(@PathVariable("id") int id, @PathVariable("pet") int pet) {
		AppUser user = appuserRepository.findById(id);
		Pet myPet = petRepository.findById(pet);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (myPet == null) {
			return new ResponseEntity<>("Pet not found", HttpStatus.NOT_FOUND);
		}
		if (user.getPet().size() > 4) {
			return new ResponseEntity<>("You can only register up to 4 pets", HttpStatus.NOT_ACCEPTABLE);
		}

		if (user.getPet().contains(myPet)) {
			user.removePet(myPet);
			// myPet.removeReportBy(user);
			// myPet.deleteReport();
			appuserRepository.save(user);
			// petRepository.save(myPet);
			return new ResponseEntity<>("Pet removed successfully", HttpStatus.OK);
		} else {
			user.addPet(myPet);
			// myPet.addReportBy(user);
			// myPet.setReport();
			appuserRepository.save(user);
			// petRepository.save(myPet);
			return new ResponseEntity<>("Pet registered successfully", HttpStatus.OK);
		}
	}

	@PutMapping(path = "/user/username/{username}/pet/{pet}")
	public ResponseEntity<String> registerPetByUsername(@PathVariable("username") String username,
			@PathVariable("pet") int pet) {
		AppUser user = appuserRepository.findByUsername(username);
		Pet myPet = petRepository.findById(pet);
		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		if (myPet == null) {
			return new ResponseEntity<>("Pet not found", HttpStatus.NOT_FOUND);
		}
		if (user.getPet().size() > 4) {
			return new ResponseEntity<>("You can only register up to 4 pets", HttpStatus.NOT_ACCEPTABLE);
		}

		if (user.getPet().contains(myPet)) {
			user.removePet(myPet);
			appuserRepository.save(user);
			return new ResponseEntity<>("Pet removed successfully", HttpStatus.OK);
		} else {
			user.addPet(myPet);
			appuserRepository.save(user);
			return new ResponseEntity<>("Pet registered successfully", HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "/user/id/{id}/report/image/{image}")
	String deleteReport(@PathVariable("id") int id, @PathVariable("image") int image) {
		AppUser user = appuserRepository.findById(id);
		Image removeImage = imageRepository.findById(image);
		user.removeReport(removeImage);
		return success;
	}
	
	@DeleteMapping(path = "/user/id/{id}")
	String deleteUser(@PathVariable("id") int id) {
		appuserRepository.deleteById(id);
		return success;
	}
}