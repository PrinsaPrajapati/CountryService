package com.prinsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.prinsa.entity.Country;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTest {

	@Test
	@Order(1)
	void getAllCountriesIntegrationTest() throws JSONException {
		
		String expected= "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"usa\",\r\n"
				+ "        \"countryCapital\": \"washington\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"countryName\": \"japan\",\r\n"
				+ "        \"countryCapital\": \"tokyo\"\r\n"
				+ "    }\r\n"
				+ "]";
		
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/countries/getcountries", String.class);
		System.out.println(response.getStatusCode());
	    System.out.println(response.getBody());	
	     //for validation
	    JSONAssert.assertEquals(expected, response.getBody(), false);
	
	}
	
	@Test
	@Order(2)
	void getAllCountryByIdIntegrationTest() throws JSONException {
		
		
		String expected= "{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"countryName\": \"India\",\r\n"
				+ "    \"countryCapital\": \"Delhi\"\r\n"
				+ "}";
				
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/countries/getcountries/1", String.class);
		System.out.println(response.getStatusCode());
	    System.out.println(response.getBody());	
	     //for validation
	    JSONAssert.assertEquals(expected, response.getBody(), false);
	
	}
	
	@Test
	@Order(3)
	void getAllCountryByNameIntegrationTest() throws JSONException {
		
		
		String expected= "{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"countryName\": \"India\",\r\n"
				+ "    \"countryCapital\": \"Delhi\"\r\n"
				+ "}";
				
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/countries/getcountries/countryname?name=India", String.class);
		System.out.println(response.getStatusCode());
	    System.out.println(response.getBody());	
	     //for validation
	    JSONAssert.assertEquals(expected, response.getBody(), false);
	
	}
	
	@Test
	@Order(4)
	void addNewCountryIntegrationTest() throws JSONException {
		
		Country country= new Country(4, "China", "Beijing");
		String expected= "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"China\",\r\n"
				+ "    \"countryCapital\": \"Beijing\"\r\n"
				+ "}";
				
		TestRestTemplate restTemplate = new TestRestTemplate();
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/countries/addcountry",request,String.class);
		System.out.println(response.getStatusCode());
	    System.out.println(response.getBody());	
	     //for validation
	    assertEquals(HttpStatus.CREATED,response.getStatusCode());
	    JSONAssert.assertEquals(expected, response.getBody(), false);
	
	}
	
	@Test
	@Order(5)
	void updateCountryIntegrationTest() throws JSONException {
		
		Country country= new Country(4, "Chinax", "xxx");
		String expected= "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"Chinax\",\r\n"
				+ "    \"countryCapital\": \"xxx\"\r\n"
				+ "}";
				
		TestRestTemplate restTemplate = new TestRestTemplate();
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,headers);
		//use exchnage there is no direct method for put
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/countries/updatecountry/4",HttpMethod.PUT ,request,String.class);
		System.out.println(response.getStatusCode());
	    System.out.println(response.getBody());	
	     //for validation
	    assertEquals(HttpStatus.OK,response.getStatusCode());
	    JSONAssert.assertEquals(expected, response.getBody(), false);
	
	}
	

	@Test
	@Order(6)
	void deleteCountryIntegrationTest() throws JSONException {
		
		Country country= new Country(4, "Chinax", "xxx");
		String expected= "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"Chinax\",\r\n"
				+ "    \"countryCapital\": \"xxx\"\r\n"
				+ "}";
				
		TestRestTemplate restTemplate = new TestRestTemplate();
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		//it will not resturn anything for assertion
	    //restTemplate.delete("http://localhost:8080/countries/deletecountries/4");
		
		HttpEntity<Country> request = new HttpEntity<Country>(country,headers);
		//use exchnage there is no direct method for put
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/countries/deletecountries/4",HttpMethod.DELETE ,request,String.class);
		//System.out.println(response.getStatusCode());
	    System.out.println(response.getBody());	
	     //for validation
	    assertEquals(HttpStatus.OK,response.getStatusCode());
	    JSONAssert.assertEquals(expected, response.getBody(), false);
	
	}
	
}
