package com.example.demo.pet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.image.Image;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

	@Query(value = "SELECT * FROM PET", nativeQuery = true)
	public List<Pet> findAll();

	@Query(value = "SELECT * FROM PET WHERE ID = :id", nativeQuery = true)
	Pet findById(@Param("id") int id);

//	@Query("SELECT p FROM PET p WHERE p.pet_id = :pet_id")
//	public List<Pet> findBypetID(@Param("pet_id") int pet_id);

	@Query(value = "SELECT * FROM PET WHERE NAME = :name", nativeQuery = true)
	public List<Pet> findByName(@Param("name") String name);

	@Query(value = "SELECT * FROM PET WHERE TYPE = :type", nativeQuery = true)
	public List<Pet> findByType(@Param("type") String type);

	@Query(value = "SELECT * FROM PET WHERE OWNER = :id", nativeQuery = true)
	public List<Pet> findByOwner(@Param("id") int id);

	@Query(value = "SELECT * FROM PET ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Pet findPetRandom();

	// @Query(value = "SELECT id FROM PET WHERE ID = :id", nativeQuery = true) //
	// single
	// Pet findSingle(@Param("id")int id);

	@Transactional
	void deleteById(int id);

}
