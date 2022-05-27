package com.example.demo.appuser;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	@Query(value = "SELECT * FROM APPUSER", nativeQuery = true)
	public List<AppUser> findAll();
	
	@Query(value = "SELECT * FROM APPUSER WHERE PRIVILEGE = 1", nativeQuery = true)
	public List<AppUser> findMod();
	
	@Query(value = "SELECT * FROM APPUSER WHERE APPLYMOD = 1", nativeQuery = true)
	public List<AppUser> findApplicant();

	@Query(value = "SELECT * FROM APPUSER WHERE ID = :id", nativeQuery = true)
	AppUser findById(@Param("id") int id);

	//@Query(value = "SELECT * FROM APPUSER A INNER JOIN FOLLOW F ON F.FOLLOWER_ID = :id", nativeQuery = true)
	//public List<AppUser> findFollower(@Param("id") int id);

	//@Query(value = "SELECT * FROM APPUSER A INNER JOIN FOLLOW F ON F.FOLLOWING_ID = :id", nativeQuery = true)
	//public List<AppUser> findFollowing(@Param("id") int id);

	@Query(value = "SELECT * FROM APPUSER WHERE USERNAME = :username", nativeQuery = true)
	AppUser findByUsername(@Param("username") String username);

	@Query(value = "SELECT * FROM APPUSER WHERE EMAIL = :email", nativeQuery = true)
	public List<AppUser> findByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM APPUSER WHERE FIRSTNAME = :firstname", nativeQuery = true)
	public List<AppUser> findByFirstname(@Param("firstname") String firstname);

	@Query(value = "SELECT * FROM APPUSER WHERE LASTNAME = :lastname", nativeQuery = true)
	public List<AppUser> findByLastname(@Param("lastname") String lastname);

	@Query(value = "SELECT PRIVILEGE FROM APPUSER WHERE USERNAME = :username", nativeQuery = true)
	Integer isMod(@Param("username") String username);
	
	@Query(value = "SELECT USERNAME FROM APPUSER A INNER JOIN FOLLOW F ON F.FOLLOWING_ID = :id WHERE A.ID = :id", nativeQuery = true)
	public Set<String> findFollower(@Param("id") int id);
	
	@Query(value = "SELECT USERNAME FROM APPUSER A INNER JOIN FOLLOW F ON F.FOLLOWER_ID = :id WHERE A.ID = :id", nativeQuery = true)
	public Set<String> findFollowing(@Param("id") int id);
	
	@Query(value = "SELECT NAME FROM PET P INNER JOIN APPUSER A ON P.OWNER = :id", nativeQuery = true)
	public Set<String> findPet(@Param("id") int id);
	
	@Transactional
	void deleteById(int id);
}
