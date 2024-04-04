package com.prinsa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prinsa.controllers.AddResponse;
import com.prinsa.entity.Country;
import com.prinsa.repository.CountryRepositoryInt;

@Service
public class CountryService {

	@Autowired
	CountryRepositoryInt countryRepo;

	public List<Country> getAllCountries() {
		List<Country> countries= countryRepo.findAll();
		return countries;
	}

	
	public Country getCountryById(int id) {
		return countryRepo.findById(id).get();

	}

	public Country getCountryByName(String countryName) {
		List<Country>countries=countryRepo.findAll();
		Country country = null;
		for(Country con: countries) {
			if(con.getCountryName().equalsIgnoreCase(countryName)) {
				country=con;
			}
		}
		return country;
	}

	public Country addCountry(Country country) {
		country.setId(getMaxId());
		return countryRepo.save(country);

	}

	public  int getMaxId() {
		return countryRepo.findAll().size()+1;
	}

	public Country updateCountry(Country country) {
		countryRepo.save(country);
		return  country;
	}

	public AddResponse deleteCountry(int id) {
		
		countryRepo.deleteById(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country deleted !");
		res.setId(id);
		return res;
	}
}
