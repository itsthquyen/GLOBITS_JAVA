package com.globits.demo.service;

import com.globits.demo.model.Country;
import com.globits.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;


    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

 
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

  
    public Country updateCountry(Long id, Country updatedCountry) {
        if (countryRepository.existsById(id)) {
            updatedCountry.setId(id);
            return countryRepository.save(updatedCountry);
        }
        return null;
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
