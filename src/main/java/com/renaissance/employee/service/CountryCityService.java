package com.renaissance.employee.service;

import com.renaissance.employee.exception.NullException;
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
        if(country == null) throw new NullException("Country object to be inserted cannot be null");
        Country countryData = countryRepo.findCountryByName(country.getCountryName());
        if(countryData != null) {
            throw new ResourceNotFoundException("Country with name  " + country.getCountryName() + " already exists");
        }
        countryRepo.save(country);
        return country;
    }
    @Override
    public Country updateCountry(Country country, int id)   {
        Country countryData = findByCountryId(id).orElseThrow(
                ()->new ResourceNotFoundException("Country not found with ID : " + id));
        countryData.setCountryName(country.getCountryName());
        countryData.setCountryCode(country.getCountryCode());
        countryRepo.save(countryData);
        return countryData;
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

    @Override
    public void deleteCountryById(int countryId)    {
        Country country = findByCountryId(countryId)
                .orElseThrow(()->
                new ResourceNotFoundException("Country not found with ID : " + countryId));
        countryRepo.deleteById(countryId);
    }
    @Override
    public List<City> getAllCities()
    {
        List<City> cities = cityRepo.findAll();
        if(cities.isEmpty())
            throw new ResourceNotFoundException("No Cities are present. Please create new City");
        return cities;
    }
    @Override
    public City addCity(City city) {
        if(city == null) throw new NullException("City object to be inserted cannot be null");

        Country country = cityRepo.findCountryByName(city.getCountry().getCountryName());
        if(country != null) {
            Optional<City> cityData = cityRepo.findCityByCityName(city.getCityName());
            if(cityData.isPresent())
                throw new ResourceNotFoundException("City with name " + city.getCityName() + " already exists");

                city.setCountry(country);
                cityRepo.save(city);
                return city;
        }
        else
            throw new ResourceNotFoundException("Not found Country with name = " + city.getCountry().getCountryName());
    }
    @Override
    public Optional<City> findByCityId(int cityId)   {
        Optional<City> city = cityRepo.findById(cityId);
        if(city.isPresent())
            return city;
        else
            throw new ResourceNotFoundException("City not found for CityId : " + cityId);
    }
    @Override
    public List<City> findCitiesByCountryId(int countryId)  {
        if(!countryRepo.existsById(countryId))
            throw new ResourceNotFoundException("No City exist with country Id : " + countryId);
        List<City> cities = cityRepo.findByCountryId(countryId);
        return cities;
    }
    @Override
    public City updateCity(City city, int cityId)   {
        City cityData = cityRepo.findById(cityId)
                        .orElseThrow(()->
                        new ResourceNotFoundException("City not found with ID : " + cityId));
        cityData.setCityName(city.getCityName());
        cityRepo.save(cityData);
        return cityData;
    }
    @Override
    public void deleteCityByCityId(int cityId)  {
       City city = findByCityId(cityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found with ID : " + cityId));

        cityRepo.deleteById(city.getCityId());
    }
    @Override
    public void deleteCityByCountryId(int countryId)    {
        if(!countryRepo.existsById(countryId))
            throw new ResourceNotFoundException("No City exist with country Id : " + countryId);
        cityRepo.deleteCityByCountryId(countryId);
    }
}
