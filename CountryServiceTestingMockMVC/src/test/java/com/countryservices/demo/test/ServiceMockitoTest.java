package com.countryservices.demo.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.countryservice.demo.entity.Country;
import com.countryservice.demo.repository.CountryRepositoryInt;
import com.countryservice.demo.services.CountryService;

//Test all the Method  by given orders with this annotation
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = { ServiceMockitoTest.class })
public class ServiceMockitoTest {

	@Mock // it will Mock the Repository
	CountryRepositoryInt countryRepo;

	@InjectMocks // it will invoke the methods for testing
	CountryService countryService;

	public List<Country> mycountries;

	@Test // for testing methods
	@Order(1) // test in order
	public void test_getAllCountries() {
		//creating our own Record 
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));
		mycountries.add(new Country(3, "Usa", "Washington"));

		// Mocking statement ,mocking external dependencies repository
		when(countryRepo.findAll()).thenReturn(mycountries);
		assertEquals(3, countryService.getAllCountries().size());
	}
	
	@Test 
	@Order(2)
	public void test_getCountryById() {
		//creating our own Record 
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));
		mycountries.add(new Country(3, "Usa", "Washington"));
		
		int countryId=1;
		// Mocking statement ,mocking external dependencies repository
		when(countryRepo.findAll()).thenReturn(mycountries);
		
		assertEquals(countryId,countryService.getCountryById(countryId).getId());
		
	}
	@Test
	@Order(3)
	public void test_getCountryByName() {
		//creating our own Record 
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));
		mycountries.add(new Country(3, "Usa", "Washington"));
		
		String countryName= "India";
		// Mocking statement ,mocking external dependencies repository
		when(countryRepo.findAll()).thenReturn(mycountries);
		
		assertEquals(countryName,countryService.getCountryByName(countryName).getCountryName());
		
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		
		//creating our own Record for Testing
		Country newCountry= new Country(4, "China", "Beijing");
        //Mocking statement
		when(countryRepo.save(newCountry)).thenReturn(newCountry);
		assertEquals(newCountry,countryService.addCountry(newCountry));
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		//creating our own Record for updateTesting
		Country updatedCountry= new Country(4, "China", "Beijing");
        //Mocking statement
		when(countryRepo.save(updatedCountry)).thenReturn(updatedCountry);
		assertEquals(updatedCountry,countryService.updateCountry(updatedCountry));
	}
	@Test
	@Order(6)
	public void test_deleteCountry() {
		
		//creating our own Record for updateTesting
		Country country= new Country(4, "China", "Beijing");
		
		countryService.deleteCountry(country);
		verify(countryRepo,times(1)).delete(country);//mocking
	}
	
}
