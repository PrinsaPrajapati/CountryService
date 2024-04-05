package com.countryservices.demo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.entity.Country;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = { ControllerMockitoTest.class })
public class ControllerMockitoTest {

	@Mock // it will Mock the Service
	CountryService countryService;

	@InjectMocks // it will invoke the methods for testing
	CountryController countryController;

	public List<Country> mycountries;
	Country country;

	@Test // for testing methods
	@Order(1) // test Execution method in order
	public void test_getAllCountries() {
		// creating our own Record
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));
		mycountries.add(new Country(3, "Usa", "Washington"));

		// Mocking statement ,mocking external dependencies service
		when(countryService.getAllCountries()).thenReturn(mycountries);
	    ResponseEntity<List<Country>> res= countryController.getcountries();
 
	    assertEquals(HttpStatus.FOUND,res.getStatusCode());
	    assertEquals(3,res.getBody().size());
	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		// creating our own Record
		 country= new Country(4, "China", "Beijing");
		
		int countryId = 4;
		// Mocking statement ,mocking external dependencies repository
		when(countryService.getCountryById(countryId)).thenReturn(country);

		ResponseEntity<Country> res= countryController.getcountriesById(countryId);
		 
	    assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(countryId,res.getBody().getId());

	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		// creating our own Record
		
		 country=new Country(1, "India", "Delhi");

		String countryName = "India";
		// Mocking statement ,mocking external dependencies repository
		when(countryService.getCountryByName(countryName)).thenReturn(country);

		ResponseEntity<Country> res= countryController.getcountryByName(countryName);
		 
	    assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(countryName,res.getBody().getCountryName());

	}

	@Test
	@Order(4)
	public void test_addCountry() {

		// creating our own Record for Testing
		country = new Country(2, "uk", "London");
		// Mocking statement
		when(countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res= countryController.addCountry(country);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
		assertEquals(country,res.getBody());

	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		// creating our own Record for updateTesting
		Country updatedCountry = new Country(3, "usa", "washington");
	   int countryId=3;
		// Mocking statement
		when(countryService.getCountryById(countryId)).thenReturn(updatedCountry);
		when(countryService.updateCountry(updatedCountry)).thenReturn(updatedCountry);
		ResponseEntity<Country> res= countryController.updateCountry(countryId,updatedCountry);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(3,res.getBody().getId());
		assertEquals("usa",res.getBody().getCountryName());
		assertEquals("washington",res.getBody().getCountryCapital());
	}

	@Test
	@Order(6)
	public void test_deleteCountry() {
		
		//creating our own Record for updateTesting
		 country=  new Country(3, "usa", "washington");
		 int countryId=3;
		 when(countryService.getCountryById(countryId)).thenReturn(country);
		 ResponseEntity<Country> res= countryController.deleteCountry(countryId);
		 assertEquals(HttpStatus.OK,res.getStatusCode());
	}

}
