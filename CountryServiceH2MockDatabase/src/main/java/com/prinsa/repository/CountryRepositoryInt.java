package com.prinsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prinsa.entity.Country;


public interface CountryRepositoryInt extends JpaRepository<Country, Integer> {

	
	
}
