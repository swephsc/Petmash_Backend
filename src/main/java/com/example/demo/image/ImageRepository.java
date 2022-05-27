package com.example.demo.image;
//import java.sql.Blob;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.appuser.AppUser;
import com.example.demo.pet.Pet;

//import com.example.demo.page.Page;

@Repository
public interface ImageRepository  extends JpaRepository<Image, Long>{

	@Query(value = "SELECT * FROM IMAGE",nativeQuery = true)
	public List<Image> findAll();
	
	@Query (value = "SELECT * FROM IMAGE WHERE ID = :id",nativeQuery = true)
	Image findById(@Param("id")int id);
	
	@Query(value = "SELECT * FROM IMAGE WHERE NAME = :name",nativeQuery = true)
	public List<Image> findByName(@Param("name")String name);
	
	@Query (value = "SELECT * FROM IMAGE ORDER BY LIKE_NUM desc LIMIT 5",nativeQuery = true)
	public List<Image> findTop();

	@Query (value = "SELECT * FROM IMAGE ORDER BY RAND() LIMIT 2", nativeQuery = true)
	public List<Image> findRandom();

	@Query (value = "SELECT USERNAME FROM APPUSER A INNER JOIN LIKE_HISTORY L ON L.IMAGE_ID = :id", nativeQuery = true)
	public List<AppUser> findWhoLiked(@Param("id") int id);

	@Query (value = "SELECT USERNAME FROM APPUSER A INNER JOIN REPORT R ON R.IMAGE_ID = :id", nativeQuery = true)
	public List<AppUser> findWhoReported(@Param("id") int id);
	
	@Query (value = "SELECT * FROM IMAGE I INNER JOIN REPORT R ON I.ID = R.IMAGE_ID", nativeQuery = true)
	public List<Image> findReport();
	
	@Query (value = "DELETE FROM REPORT WHERE IMAGE_ID = :id" , nativeQuery = true)
	void deleteImage(@Param("id") int id);
	
//	@Query (value = "SELECT * FROM IMAGE WHERE FILE = :file", nativeQuery = true)
//	Image findByFile(@Param("file") Blob file);
	
	//@Query (value = SELECT * FROM IMAGE I INNER JOIN PAGE P ON P.
	//public List<Image> findAllImageByPage(@Param("id") int id);
	
	@Transactional
	void deleteById(int id);
}
