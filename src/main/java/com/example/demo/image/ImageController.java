package com.example.demo.image;

import java.io.File;
import java.io.IOException;
import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.io.IOException;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRepository;
import com.example.demo.page.Page;
import com.example.demo.page.PageRepository;
//import com.example.demo.page.PageRepository;
//import com.example.demo.pet.PetRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.pet.Pet;
import com.example.demo.pet.PetRepository;

@RestController
public class ImageController {

	@Autowired
	PetRepository petRepository;

	@Autowired
	AppUserRepository appuserRepository;

	@Autowired
	PageRepository pageRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	HttpServletRequest httpServletRequest;

//	@Autowired
//	ImageService service;

	// private static String uploadPath = "/uploadedFiles/";

	private String success = "{\"message\":\"success\"}";
	private String failure = "{\"message\":\"failure\"}";

	@GetMapping(path = "/image")
	List<Image> getAllImage() {
		return imageRepository.findAll();
	}

	@GetMapping(path = "/image/report")
	List<Image> getAllReport() {
		return imageRepository.findReport();
	}

	@GetMapping(path = "/image/id/{id}/file")
	URL imageUrl(@PathVariable("id") int id) {
		Image image = imageRepository.findById(id);
		URL url = getClass().getResource(image.getPath());
		return url;
	}
/*
	@GetMapping(path = "/image/id/{id}")
	Image getImageInfoById(@PathVariable Long id) {
		return service.getImageInfoById(id);
	}

	@GetMapping(path = "/image/id/{id}/file")
	byte[] getImageById(@PathVariable Long id) throws IOException {
		return service.getImageFileById(id);
	}
*/
	@GetMapping(path = "/image/id/{id}/pet")
	Pet getPetInfo(@PathVariable("id") int id) {
		Image image = imageRepository.findById(id);
		//Pet pet = imageRepository.findAllPetByImage(id);
		Pet pet = image.getPage().getPet();
		return pet;
	}
		
/*
	@PostMapping(path = "/image")
	public Image addNewImageWithFile(@RequestParam("name") String name, @RequestParam("info") String info,
			@RequestParam("image") String base64img) throws IOException {
		return service.postImage(name, info, base64img);
	}
*/
	@PostMapping(path = "/newimage")
	public String addNewImage(@RequestBody Image image ) {
		if(image == null) {
			return failure;
		}
		//image.setRandomPath();
		imageRepository.save(image);
		return success;
	}
	
	@PutMapping(path = "/image/id/{id}/randomImage")
	public String assignRandomImage(@PathVariable("id") int id) {
		
		Image image = imageRepository.findById(id);
		if(image == null) {
			return failure;
		}
		image.setRandomPath();
		imageRepository.save(image);
		return success;
	}
	
	// just in case
//	@GetMapping(path = "/image/file/{file}")
//	Image getImageByFile(@PathVariable("file") Blob file) {
//		return imageRepository.findByFile(file);
//	}

//	@GetMapping(path = "/image/page/{id}")
//	List<Image> getAllImageByPage(@PathVariable("id") int id){
	// return imageRepository.findAllImageByPage(id);
	// }

	@GetMapping(path = "/image/name/{name}")
	List<Image> getImageByName(@PathVariable("name") String name) {
		List<Image> imageResult = imageRepository.findByName(name);
		return imageResult;
	}

	@GetMapping(path = "/image/top")
	List<Image> getTop() {
		List<Image> fiveResult = imageRepository.findTop();
		return fiveResult;
	}

	@GetMapping(path = "/image/rand")
	List<Image> getRandom() {

		List<Image> imageResult = new ArrayList<Image>();
		imageResult = imageRepository.findRandom();
		return imageResult;
	}

	// get who liked the image
	@GetMapping(path = "/image/id/{id}/likeBy")
	List<AppUser> getWhoLiked(@PathVariable("id") int id) {
		List<AppUser> likeBy = imageRepository.findWhoLiked(id);
		return likeBy;
	}

	// get who reported the image
	@GetMapping(path = "/image/id/{id}/reportBy")
	List<AppUser> getWhoReported(@PathVariable("id") int id) {
		List<AppUser> reportBy = imageRepository.findWhoReported(id);
		return reportBy;
	}

	/*
	 * @PostMapping(path = "/image") String createImage(@RequestBody Image image) {
	 * if (image == null) { return failure; } else { imageRepository.save(image);
	 * return success; } }
	 */
	/*
	 * @PutMapping(path = "/image/id/{id}/file") public ResponseEntity<String>
	 * uploadImage(@PathVariable("id") int id, @RequestParam("file") MultipartFile
	 * file) throws IOException { if (file.isEmpty()) { return new
	 * ResponseEntity<>("Please upload a proper image", HttpStatus.NO_CONTENT); }
	 * 
	 * String fullPath =
	 * httpServletRequest.getServletContext().getRealPath(uploadPath); if (!new
	 * File(fullPath).exists()) { new File(fullPath).mkdir(); }
	 * 
	 * fullPath += ("image_" + id + "_" + file.getOriginalFilename());
	 * System.out.println(fullPath);
	 * 
	 * Image image = imageRepository.findById(id); String currentPath =
	 * image.getPath();
	 * 
	 * if (currentPath != null) { File currentFile = new File(currentPath); if
	 * (currentFile.exists()) { currentFile.delete(); } }
	 * 
	 * image.setPath(fullPath); imageRepository.save(image);
	 * 
	 * File temp = new File(fullPath); file.transferTo(temp);
	 * 
	 * return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK); }
	 * 
	 * @GetMapping(path = "image/id/{id}/file") public ResponseEntity<Resource>
	 * getImageFile(@PathVariable("id") int id) throws IOException { Image image =
	 * imageRepository.findById(id);
	 * 
	 * if (image == null || image.getPath() == null) { return null; }
	 * 
	 * File file = new File(image.getPath()); if (!file.exists()) { return null; }
	 * 
	 * String[] splitPath = image.getPath().split("/"); String fileName =
	 * splitPath[splitPath.length - 1]; // add headers to state that a file is being
	 * downloaded HttpHeaders header = new HttpHeaders();
	 * header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
	 * fileName); header.add("Cache-Control",
	 * "no-cache, no-store, must-revalidate"); header.add("Pragma", "no-cache");
	 * header.add("Expires", "0");
	 * 
	 * // convert the file into bytearrayresource format to send to the front end
	 * with // the file Path path = Paths.get(file.getAbsolutePath());
	 * ByteArrayResource data = new ByteArrayResource(Files.readAllBytes(path));
	 * 
	 * // send the response entity back to the front end with the return
	 * ResponseEntity.ok().headers(header).contentLength(file.length())
	 * .contentType(MediaType.parseMediaType("application/octet-stream")).body(data)
	 * ; }
	 * 
	 * @PutMapping(path = "/image/id/{id}") public ResponseEntity<String>
	 * updateImage(@PathVariable("id") int id, @RequestBody Image request) { Image
	 * image = imageRepository.findById(id); if (image == null) { return new
	 * ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND); } if
	 * (request.getName() != null) { image.setName(request.getName()); } if
	 * (request.getInfo() != null) { image.setInfo(request.getInfo()); }
	 * imageRepository.save(image); return new
	 * ResponseEntity<>("Image info update successful", HttpStatus.OK); }
	 */
	
	//@PutMapping(path = "/image/id/{id}/report/username/{username}")
	//@PutMapping(path = "/image/id/{id}/report/id/{id}")
	
	@PutMapping(path = "/image/id/{id}/page/{page}")
	String givePage(@PathVariable("id") int id, @PathVariable("page") int page) {
		Image image = imageRepository.findById(id);
		Page pg = pageRepository.findById(page);
		if(image == null || pg == null) {
			return failure;
		}
		image.setPage(pg);
		imageRepository.save(image);
		return success;
	}
	
	
	@DeleteMapping(path = "/image/id/{id}/removeReport/{report}")
	String deleteReport(@PathVariable("id") int id, @PathVariable("report") int report) {
		Image image = imageRepository.findById(id);
		
		
		if(image == null) {
			return failure;
		}
		image.deleteReport();		//decrement the number of reports for the image
		imageRepository.deleteImage(id);
		imageRepository.save(image);
		return success;
	}
	
	@DeleteMapping(path = "/image/id/{id}")
	String deleteImage(@PathVariable("id") int id) {
		Image image = imageRepository.findById(id);

		if (image == null) {
			return failure;
		}

		//File currentFile = new File(image.getPath());
		//if (currentFile != null && currentFile.exists())
		//	currentFile.delete();

		imageRepository.deleteById(id);
		return success;
	}
}
