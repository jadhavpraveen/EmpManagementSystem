package com.renaissance.employee.controller;

import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Country;
import com.renaissance.employee.service.CountryCityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class CountryCityController {
    @Autowired
    CountryCityService service;
   @PostMapping(value = "countries")
    public ResponseEntity<Country> addCountry(@Valid @RequestBody Country country) {
        service.addCountry(country);
        return new ResponseEntity<>(country, HttpStatus.CREATED);
    }

    @GetMapping("countries")
    public ResponseEntity<List<Country>> getCountries()   {
        List<Country> countries = service.getAllCountries();
        if(countries.isEmpty())
            throw new ResourceNotFoundException("No Countries are present. Please create new Country");
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
    @GetMapping("countries/{countryId}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "countryId") int countryId) {
        Optional<Country> country = service.findByCountryId(countryId);
        if(country.isPresent())
            return new ResponseEntity<>(country.get(), HttpStatus.OK);
        else
            throw new ResourceNotFoundException("Country not found for CountryId : " + countryId);
    }
    @PutMapping("countries/{id}")
    public ResponseEntity<Country> updateCountry(@Valid @RequestBody Country country, @PathVariable("id") int id)  {
          return new ResponseEntity<>(service.updateCountry(country, id), HttpStatus.OK);
    }
    @DeleteMapping("countries/{id}")
    public ResponseEntity<HttpStatus> deleteCountryById(@PathVariable("id") int id) {
        service.deleteCountryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(value = "cities")
    public ResponseEntity<City> addCity(@Valid  @RequestBody City city) {
        service.addCity(city);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }

    @GetMapping("cities")
    public ResponseEntity<List<City>> getCities()   {
        List<City> cities = service.getAllCities();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("cities/{cityId}")
    public ResponseEntity<City> getCityById(@PathVariable(value = "cityId") int cityId)   {
        Optional<City> city = service.findByCityId(cityId);
            return new ResponseEntity<>(city.get(), HttpStatus.OK);
    }
    @GetMapping("cities/country/{countryId}")
    public ResponseEntity<List<City>> getCitiesByCountryId(@PathVariable(value = "countryId") int countryId)   {
        List<City> cities = service.findCitiesByCountryId(countryId);
        if(cities.isEmpty())
            throw new ResourceNotFoundException("No Cities are present for CountryID :" + countryId);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
    @PutMapping("cities/{id}")
    public ResponseEntity<City> updateCity(@Valid @RequestBody City city,@PathVariable("id") int id)  {
        return new ResponseEntity<>(service.updateCity(city, id), HttpStatus.OK);
    }
    @DeleteMapping("cities/{id}")
    public ResponseEntity<HttpStatus> deleteCityByCityId(@PathVariable("id") int id) {
        service.deleteCityByCityId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("cities/countries/{id}")
    public ResponseEntity<HttpStatus> deleteCityByCountryId(@PathVariable("id") int id) {
        service.deleteCityByCountryId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
