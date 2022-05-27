package com.example.demo.page;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

	@Query(value = "SELECT * FROM PAGE", nativeQuery = true)
	public List<Page> findAll();

	@Query(value = "SELECT * FROM PAGE WHERE ID = :id", nativeQuery = true)
	Page findById(@Param("id") int id);

	@Query(value = "SELECT * FROM PAGE WHERE name = :name", nativeQuery = true)
	public List<Page> findByName(@Param("name") String name);

	// @Query(value = "SELECT * FROM IMAGE ")

	@Transactional
	void deleteById(int id);

}
