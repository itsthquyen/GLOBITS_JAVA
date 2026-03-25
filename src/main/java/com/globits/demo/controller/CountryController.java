package com.globits.demo.controller;

import com.globits.demo.model.Country;
import com.globits.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // CREATE 
    @PostMapping
    public Country create(@RequestBody Country country) {
        return countryService.createCountry(country);
    }


    // GET 
    @GetMapping
    public List<Country> getAll() {
        return countryService.getAllCountries();
    }

    // GET 
    @GetMapping("/{id}")
    public Country getById(@PathVariable("id") Long id) {
        Optional<Country> country = countryService.getCountryById(id);
        return country.orElse(null); 
    }

    // UPDATE 
    // PUT 
    @PutMapping("/{id}")
    public Country update(@PathVariable("id") Long id, @RequestBody Country country) {
        return countryService.updateCountry(id, country);
    }

   
    // DELETE 
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        countryService.deleteCountry(id);
        return "Đã xóa thành công Country có ID: " + id;
    }
}
