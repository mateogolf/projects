package com.mattr.projects.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.mattr.projects.models.Pack;

public interface PackRepo extends CrudRepository<Pack,Long>{
//	@Query("SELECT p FROM Pack d JOIN d.ninjas n")
//    List<Object[]> joinDojosAndNinjas2();
	ArrayList<Pack> findByAvailabilityTrue();

}
