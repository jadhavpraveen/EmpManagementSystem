package com.renaissance.employee.service;

import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Country;
import com.renaissance.employee.repository.CityRepository;
import com.renaissance.employee.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryCityService implements ICountryCityService {

    @Autowired
    CountryRepository countryRepo;
    @Autowired
    CityRepository cityRepo;
    @Override
    public Country addCountry(Country country) {
        Country countryData = countryRepo.findCountryByName(country.getCountryName());
        if(countryData != null) {
            throw new ResourceNotFoundException("Country with name  " + country.getCountryName() + " already exists");
        }
        countryRepo.save(country);
        return country;
    }

    @Override
    public List<Country> getAllCountries()
    {
        List<Country> countries = countryRepo.findAll();
        return countries;
    }
    @Override
    public Optional<Country> findByCountryId(int countryId)   {
        Optional<Country> country = countryRepo.findById(countryId);
        return country;
    }

    public void deleteCountryById(int countryId)    {
        countryRepo.deleteById(countryId);
    }
    @Override
    public List<City> getAllCities()
    {
        List<City> cities = cityRepo.findAll();
        return cities;
    }
    @Override
    public City addCity(City city) {
        Country country = countryRepo.findCountryByName(city.getCountry().getCountryName());

        if(country != null) {
            Optional<City> cityData = cityRepo.findCityByCityName(city.getCityName());
            if(cityData.isPresent())
                throw new ResourceNotFoundException("City with name " + city.getCityName() + " already exists");
                city.setCountry(country);
                cityRepo.save(city);
        }
        else
            throw new ResourceNotFoundException("Not found Country with name = " + city.getCountry().getCountryName());

        return city;
    }
    @Override
    public Optional<City> findCityByCityId(int cityId)   {
        Optional<City> city = cityRepo.findById(cityId);
        return city;
    }
    @Override
    public List<City> findCitiesByCountryId(int countryId)  {
        if(!countryRepo.existsById(countryId))
            throw new ResourceNotFoundException("No City exist with country Id : " + countryId);
        List<City> cities = cityRepo.findByCountryId(countryId);
        return cities;
    }
    @Override
    public void deleteCityByCityId(int cityId)  {
        cityRepo.deleteById(cityId);
    }
    @Override
    public void deleteCityByCountryId(int countryId)    {
        if(!countryRepo.existsById(countryId))
            throw new ResourceNotFoundException("No City exist with country Id : " + countryId);
        cityRepo.deleteCityByCountryId(countryId);
    }

    public static class JwtService {
    }
}
