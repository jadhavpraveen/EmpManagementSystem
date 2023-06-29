package com.renaissance.country;

import com.renaissance.employee.exception.NullException;
import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Country;
import com.renaissance.employee.repository.CityRepository;
import com.renaissance.employee.repository.CountryRepository;
import com.renaissance.employee.service.CountryCityService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)

public class CityServiceTest {
    @Mock  // used for mocking the object upon which it depends
    CountryRepository countryRepository;
    @Mock  // used for mocking the object upon which it depends
    CityRepository cityRepository;
    @InjectMocks // used to create mock object of the class which we actually want to test
    CountryCityService cityService;

    @Test
    public void should_return_no_city_if_city_list_is_empty()  throws Exception  {
        List<City> cities = new ArrayList<City>();
        when(cityRepository.findAll()).thenReturn(cities);
        ResourceNotFoundException exception = Assert.assertThrows(
                "No Cities are present. Please create new City",
                ResourceNotFoundException.class,
                ()->cityService.getAllCities());
        Assertions.assertEquals("No Cities are present. Please create new City", exception.getMessage());
    }

    @Test
    public void should_return_city_list()  throws Exception  {
        List<City> cities = new ArrayList<>(Arrays.asList(
                new City(
                        1,
                        "Mumbai",
                        new Country(
                                1,
                                "India",
                                "IND"
                        )),
                new City(
                        2,
                        "Sydney",
                        new Country(
                                2,
                                "Australia",
                                "AUS"
                        )),
                new City(
                        3,
                        "London",
                        new Country(
                                3,
                                "England",
                                "ENG"
                        )
                )));

        when(cityRepository.findAll()).thenReturn(cities);
        List<City> cityList = cityService.getAllCities();

        Assertions.assertNotNull(cityList);
        Assertions.assertEquals(cities, cityList);
        Assertions.assertEquals(cities.isEmpty(),cityList.isEmpty());
        Assertions.assertEquals(cities.size(), cityList.size());
    }
    @Test
    public void save_city_should_throw_null() throws Exception {
        NullException exception = Assert.assertThrows(
                "City object to be inserted cannot be null",
                NullException.class,
                ()->cityService.addCity(null));
        Assertions.assertEquals(
                "City object to be inserted cannot be null", exception.getMessage());
    }

    @Test
    public void save_city_should_throw_resource_not_found_exception_if_country_does_not_exist() throws Exception    {
        City city1 = new City(
                    1,
                    "Mumbai",
                    new Country(
                            1,
                            "India",
                            "IND"
                    ));

        when(cityRepository.findCountryByName(
                city1.getCountry().getCountryName()))
                .thenReturn(null);
        ResourceNotFoundException exception = Assert.assertThrows(
                "Not found Country with name = " + city1.getCountry().getCountryName(),
                ResourceNotFoundException.class,
                ()->cityService.addCity(city1));
        Assertions.assertEquals(
                "Not found Country with name = India", exception.getMessage());
    }
    @Test
    public void save_city_should_return_country_object_if_country_exist() throws Exception    {
        City city1 = new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                ));
        doReturn(city1.getCountry()).
                when(cityRepository).
                findCountryByName(city1.getCountry().getCountryName());
        City city = cityService.addCity(city1);

        Assertions.assertEquals(city1.getCountry(), city.getCountry());
        Assertions.assertEquals(city1.getCountry().getCountry_Id(), city.getCountry().getCountry_Id());
        Assertions.assertEquals(city1.getCountry().getCountryName(), city.getCountry().getCountryName());
        Assertions.assertEquals(city1.getCountry().getCountryCode(), city.getCountry().getCountryCode());
    }
    @Test
    public void save_city_should_throw_resource_not_found_exception_if_city_already_exist() throws Exception    {
        City city1 = new City(
                    1,
                    "Mumbai",
                    new Country(
                            1,
                            "India",
                            "IND"
                    ));
        doReturn(Optional.of(city1)).when(cityRepository).findCityByCityName(city1.getCityName());
        doReturn(city1.getCountry()).
                when(cityRepository).
                findCountryByName(city1.getCountry().getCountryName());

        ResourceNotFoundException exception = Assert.assertThrows(
                    "City with name " + city1.getCityName() + " already exists",
                    ResourceNotFoundException.class,
                    ()->cityService.addCity(city1));
        Assertions.assertEquals(
                    "City with name Mumbai already exists", exception.getMessage());
    }
    @Test
    public void add_city_should_save_city_object_successfully() throws Exception    {
        City city1 = new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                ));
        doReturn(city1.getCountry()).
                when(cityRepository).
                findCountryByName(city1.getCountry().getCountryName());
        doReturn(Optional.ofNullable(null)).when(cityRepository).findCityByCityName(city1.getCityName());

        City city = cityService.addCity(city1);

        Assertions.assertEquals(city1, city);
        Assertions.assertEquals(city1.getCityId(), city.getCityId());
        Assertions.assertEquals(city1.getCityName(), city.getCityName());
        Assertions.assertEquals(city1.getCountry(), city.getCountry());
    }
    @Test
    public void get_city_by_cityId_should_throw_exception_if_cityId_not_found() throws Exception    {
        City city1 = new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                ));
       doReturn(Optional.ofNullable(null)).when(cityRepository).findById(0);

        ResourceNotFoundException exception = Assert.assertThrows(
                "City not found for CityId : " + city1.getCityId(),
                ResourceNotFoundException.class,
                ()->cityService.findByCityId(0));

        Assertions.assertEquals(
                "City not found for CityId : 0", exception.getMessage());
    }
    @Test
    public void get_city_by_cityId_should_return_city_object() throws Exception    {
        City expectedCity = new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                ));
        when(cityRepository.findById(expectedCity.getCityId())).thenReturn(Optional.of(expectedCity));

        Optional<City> actualCity = cityService.findByCityId(expectedCity.getCityId());
        Assertions.assertNotNull(actualCity);
        Assertions.assertEquals(expectedCity, actualCity.get());
        Assertions.assertEquals(expectedCity.getCityId(), actualCity.get().getCityId());
        Assertions.assertEquals(expectedCity.getCityName(), actualCity.get().getCityName());
    }
    @Test
    public void find_cities_by_countryId_should_throw_exception_if_countryId_not_found() throws Exception    {
        City city1 = new City(
                1,
                "Mumbai",
                new Country(
                        0,
                        "India",
                        "IND"
                ));
        doReturn(false).when(countryRepository).existsById(city1.getCountry().getCountry_Id());

        ResourceNotFoundException exception = Assert.assertThrows(
                "No City exist with country Id : " + city1.getCountry().getCountry_Id(),
                ResourceNotFoundException.class,
                ()->cityService.findCitiesByCountryId(0));

        Assertions.assertEquals(
                "No City exist with country Id : 0", exception.getMessage());
    }
    @Test
    public void get_cities_by_cityId_should_return_cities_list_object() throws Exception    {

        List<City> expectedCities = new ArrayList<>(Arrays.asList(
                new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                )),
                new City(
                        2,
                        "Delhi",
                        new Country(
                                1,
                                "India",
                                "IND"
                        )
                )));
        doReturn(true).when(countryRepository).existsById(1);
        when(cityRepository.findByCountryId(1)).thenReturn(expectedCities);

        List<City> actualCity = cityService.findCitiesByCountryId(1);
        Assertions.assertNotNull(actualCity);
        Assertions.assertEquals(expectedCities, actualCity);
        Assertions.assertEquals(expectedCities.size(), actualCity.size());
    }
    @Test
    public void should_return_resource_not_found_exception_if_no_city_for_update_city() throws Exception {
        City city = new City();
        ResourceNotFoundException exception = Assert.assertThrows(
                "City not found with ID : " + city.getCityId(),
                ResourceNotFoundException.class,
                ()->cityService.updateCity(city,0));
        Assertions.assertEquals("City not found with ID : 0", exception.getMessage());
    }

    @Test
    public void update_city_if_city_object_is_given() throws Exception {
        City city2 = new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                ));

        when(cityRepository.findById(1)).thenReturn(Optional.of(city2));
        city2.setCityName("New Delhi");

        City updatedCityData = cityService.updateCity(city2,city2.getCityId());
        Assertions.assertNotNull(updatedCityData);
        Assertions.assertEquals("New Delhi",updatedCityData.getCityName());
    }

    @Test
    public void delete_city_byId_should_return_resource_not_found_exception() throws Exception  {
        City city = new City();
        ResourceNotFoundException exception = Assert.assertThrows(
                "City not found for CityId : " + city.getCityId(),
                ResourceNotFoundException.class,
                ()->cityService.deleteCityByCityId(0));
        Assertions.assertEquals("City not found for CityId : 0", exception.getMessage());
    }
    @Test
    public void delete_city_if_city_id_is_given() throws Exception {
        City city1 = new City(
                1,
                "Mumbai",
                new Country(
                        1,
                        "India",
                        "IND"
                ));
        when(cityRepository.findById(city1.getCityId())).thenReturn(Optional.of(city1));

        Optional<City> city2 = cityService.findByCityId(city1.getCityId());
        cityService.deleteCityByCityId(city2.get().getCityId());
        verify(cityRepository, times(1)).deleteById(city2.get().getCityId());
    }
}
