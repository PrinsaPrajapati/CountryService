package com.countryservice.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countryservice.demo.entity.Country;
import com.countryservice.demo.services.CountryService;

@RestController
//@RequestMapping("/countries")
public class CountryController {

	@Autowired
	CountryService countryservice;

	@GetMapping("/getcountries")
	public ResponseEntity<List<Country>> getcountries() {
		try {
			List<Country> countries = countryservice.getAllCountries();
			return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getcountries/{id}")
	public ResponseEntity<Country> getcountriesById(@PathVariable(value = "id") int id) {
		try {
			Country country = countryservice.getCountryById(id);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getcountries/countryname")
	public ResponseEntity<Country> getcountryByName(@RequestParam(value = "name") String countryName) {
		try {
			Country country = countryservice.getCountryByName(countryName);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deletecountries/{id}")
	public ResponseEntity<Country> deleteCountry(@PathVariable(value = "id") int id) {
		Country country = null;
		try {
			 country = countryservice.getCountryById(id);
			 countryservice.deleteCountry(country);
			 return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/addcountry")
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		try {
			country = countryservice.addCountry(country);
			return new ResponseEntity<Country>(country, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/updatecountry/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country) {

		try {
			Country exitedCountry = countryservice.getCountryById(id);
			
			exitedCountry.setCountryName(country.getCountryName());
			exitedCountry.setCountryCapital(country.getCountryCapital());
			
			Country updatedCountry = countryservice.updateCountry(exitedCountry);
			return new ResponseEntity<Country>(updatedCountry, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

}
