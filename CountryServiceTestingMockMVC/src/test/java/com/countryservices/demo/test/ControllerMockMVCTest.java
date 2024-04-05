package com.countryservices.demo.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.entity.Country;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = "com.countryservices.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ControllerMockMVCTest.class })
public class ControllerMockMVCTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	public List<Country> mycountries;
	Country country;

	@BeforeEach // before starting or invocking each and every method this method will execute first
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}

	@Test // for testing methods
	@Order(1) // test Execution method in order
	public void test_getAllCountries() throws Exception {
		// creating our own Record
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "Uk", "London"));
		mycountries.add(new Country(3, "Usa", "Washington"));

		// Mocking statement ,mocking external dependencies service
	   when(countryService.getAllCountries()).thenReturn(mycountries);
	   this.mockMvc.perform(get("/getcountries"))
	                .andExpect(status()
	                .isFound()).andDo(print());
	
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() throws Exception {
		// creating our own Record
	     country= new Country(4, "China", "Beijing");
		 int countryId = 4;
		 // Mocking statement ,mocking external dependencies repository
		 when(countryService.getCountryById(countryId)).thenReturn(country);
		 this.mockMvc.perform(get("/getcountries/{id}",countryId))
         .andExpect(status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath(".id").value(4))
         .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("China"))
         .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Beijing"))
         .andDo(print());

	}
	@Test
	@Order(3)
	public void test_getCountryByName() throws Exception {
		// creating our own Record
	     country= new Country(4, "China", "Beijing");
		 String countryName ="China";
		 // Mocking statement ,mocking external dependencies repository
		 when(countryService.getCountryByName(countryName)).thenReturn(country);
		 this.mockMvc.perform(get("/getcountries/countryname").param("name", "China"))
         .andExpect(status().isOk())
         .andExpect(MockMvcResultMatchers.jsonPath(".id").value(4))
         .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("China"))
         .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Beijing"))
         .andDo(print());

	}
	
	@Test
	@Order(4)
	public void test_addCountry() throws Exception {
		// creating our own Record
	     country= new Country(5, "Germany", "Berlin");
		
		 // Mocking statement ,mocking external dependencies repository
		 when(countryService.addCountry(country)).thenReturn(country);
		 
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonBody = mapper.writeValueAsString(country);
		 
		 this.mockMvc.perform(post("/addcountry")
				 .content(jsonBody)
				 .contentType(MediaType.APPLICATION_JSON))
		         .andExpect(status().isCreated())
		         .andDo(print());
        
	}
	@Test
	@Order(5)
	public void test_updateCountry() throws Exception {
		// creating our own Record for updateTesting
		 country = new Country(3, "Japan", "Tokyo");
	   int countryId=3;
		// Mocking statement
		when(countryService.getCountryById(countryId)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);
		
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonBody = mapper.writeValueAsString(country);
		 
		 this.mockMvc.perform(put("/updatecountry/{id}",countryId)
				 .content(jsonBody)
				 .contentType(MediaType.APPLICATION_JSON))
		         .andExpect(status().isOk())
		         .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
		         .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Tokyo"))
		         .andDo(print());
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception {
		
		//creating our own Record for updateTesting
		 country=  new Country(6, "usa", "washington");
		 int countryId=6;
		// Mocking statement
		 when(countryService.getCountryById(countryId)).thenReturn(country);
		
		 this.mockMvc.perform(delete(("/deletecountries/{id}"),countryId))
				     .andExpect(status().isOk());
	}

}
