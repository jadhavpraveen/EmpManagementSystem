package com.renaissance.employee.service;


import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Country;

import java.util.List;
import java.util.*;

public interface ICountryCityService {
    public Country addCountry(Country country);
    public List<Country> getAllCountries();
    public Optional<Country> findByCountryId(int countryId);
    public Country updateCountry(Country country, int countryId);
    public void deleteCountryById(int countryId);
    public List<City> getAllCities();
    public City addCity(City city);
    public Optional<City> findCityByCityId(int cityId);
    public List<City> findCitiesByCountryId(int countryId);
    public void deleteCityByCityId(int cityId);
    public void deleteCityByCountryId(int countryId);
}
