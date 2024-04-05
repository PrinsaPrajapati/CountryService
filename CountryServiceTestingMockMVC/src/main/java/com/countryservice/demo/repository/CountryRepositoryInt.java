package com.countryservice.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.countryservice.demo.entity.Country;

@Repository
public interface CountryRepositoryInt extends JpaRepository<Country, Integer> {

}
