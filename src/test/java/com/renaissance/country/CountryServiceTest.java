package com.renaissance.country;


import com.renaissance.employee.exception.NullException;
import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.Country;
import com.renaissance.employee.repository.CountryRepository;
import com.renaissance.employee.service.CountryCityService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
    @Mock  // used for mocking the object upon which it depends
    CountryRepository countryRepository;
    @InjectMocks // used to create mock object of the class which we actually want to test
    CountryCityService countryCityService;

    @Test
    public void save_country() throws Exception {
        Country country =  new Country(
                1,
                "South Africa",
                "RSA");
        when(countryRepository.save(country)).thenReturn(country);
        Country country1 = countryCityService.addCountry(country);
        Assert.assertNotNull(country1);
        Assert.assertEquals(country, country1);
        Assert.assertEquals(country.getCountry_Id(), country1.getCountry_Id());
        Assert.assertEquals(country.getCountryName(), country1.getCountryName());
    }
    @Test
    public void save_country_should_throw_null() throws Exception {
        NullException exception = Assert.assertThrows(
                "Object to be inserted cannot be null",
                NullException.class,
                ()->countryCityService.addCountry(null));
    }
    @Test
    public void save_country_should_throw_resource_not_found_exception_if_country_name_exists() throws Exception {
        Country country =  new Country(
                1,
                "South Africa",
                "RSA");
        when(countryRepository.findCountryByName(country.getCountryName())).thenReturn(country);
        ResourceNotFoundException exception = Assert.assertThrows(
                "Country with name  " + country.getCountryName() + " already exists",
                ResourceNotFoundException.class,
                ()->countryCityService.addCountry(country));

        String expectedException = "Country with name  South Africa already exists";
        String actualException = "Country with name  " + country.getCountryName() + " already exists";
        Assert.assertEquals(expectedException, actualException);
    }
    @Test
    public void country_should_be_saved_in_database() throws Exception {
        Country expectedCountry =  new Country(
                1,
                "South Africa",
                "RSA");
        when(countryRepository.save(expectedCountry)).thenReturn(expectedCountry);
        Country actualCountry = countryCityService.addCountry(expectedCountry);
        Assert.assertEquals(expectedCountry, actualCountry);
        Assert.assertEquals(expectedCountry.getCountry_Id(), actualCountry.getCountry_Id());
        Assert.assertEquals(expectedCountry.getCountryName(), actualCountry.getCountryName());
        Assert.assertEquals(expectedCountry.getCountryCode(), actualCountry.getCountryCode());
    }
    @Test
    public void should_return_no_country_if_country_list_is_empty()  throws Exception  {
        List<Country> countries = new ArrayList<Country>();

        when(countryRepository.findAll()).thenReturn(countries);
        List<Country> countryList = countryCityService.getAllCountries();

        Assert.assertNotNull(countryList);
        Assert.assertEquals(countryList.isEmpty(),countryList.isEmpty());
        Assert.assertEquals(countryList.size(), countryList.size());

    }
    @Test
    public void should_return_country_list()  throws Exception  {
        List<Country> countries = new ArrayList<>(Arrays.asList(
                new Country(
                        1,
                        "India",
                        "IND"),
                new Country(
                        2,
                        "Australia",
                        "AUS"),
                new Country(
                        3,
                        "England",
                        "ENG")
        ));

        when(countryRepository.findAll()).thenReturn(countries);
        List<Country> countryList = countryCityService.getAllCountries();

        Assert.assertNotNull(countryList);
        Assert.assertEquals(countries, countryList);
        Assert.assertEquals(countryList.isEmpty(),countryList.isEmpty());
        Assert.assertEquals(countryList.size(), countryList.size());

    }
    @Test
    public void should_return_country_if_countryId_is_provided() throws Exception {
        Country country1 = new Country(
                1,
                "India",
                "IND");
        Country country2 = new Country(
                2,
                "Australia",
                "AUS");

        when(countryRepository.findById(2)).thenReturn(Optional.of(country2));
        Optional<Country> countryById = countryCityService.findByCountryId(country2.getCountry_Id());

        Assert.assertNotNull(countryById);
        Assert.assertEquals(countryById, (Optional.of(country2)));
        Assert.assertEquals(country2.getCountryName(), countryById.get().getCountryName());
        Assert.assertEquals(country2.getCountryCode(), countryById.get().getCountryCode());
    }
    @Test
    public void should_return_no_country_if_countryId_is_not_provided() throws Exception {
        Country country = new Country();
        when(countryRepository.findById(0)).thenReturn(Optional.of(country));
        Optional<Country> countryById = countryCityService.findByCountryId(0);

        Assert.assertNotNull(countryById);
        Assert.assertEquals(countryById, (Optional.of(country)));
        Assert.assertEquals(country.getCountryName(), countryById.get().getCountryName());
        Assert.assertEquals(country.getCountryCode(), countryById.get().getCountryCode());
    }
    @Test
    public void should_return_no_country_for_update_country() throws Exception {
        Country country = new Country();
        ResourceNotFoundException exception = Assert.assertThrows(
                "Country not found with ID : " + country.getCountry_Id(),
                ResourceNotFoundException.class,
                ()->countryCityService.updateCountry(country,0));
        Assert.assertEquals("Country not found with ID : 0", exception.getMessage());
    }
    @Test
    public void update_country_if_country_object_is_given() throws Exception {
        Country country =  new Country(
                1,
                "South Africa",
                "RSA");
        Country country2 = new Country(
                2,
                "Australia",
                "AUS");

        when(countryRepository.findById(2)).thenReturn(Optional.of(country2));
        country2.setCountryName("India");
        country2.setCountryCode("IND");
        Country updatedCountryData = countryCityService.updateCountry(country2,country2.getCountry_Id());
        Assert.assertNotNull(updatedCountryData);
        Assert.assertEquals("India",updatedCountryData.getCountryName());
        Assert.assertEquals("IND", updatedCountryData.getCountryCode());
    }
    @Test
    public void should_return_no_country_for_delete_country() throws Exception {
        Country country = new Country();
        ResourceNotFoundException exception = Assert.assertThrows(
                "Country not found with ID : " + country.getCountry_Id(),
                ResourceNotFoundException.class,
                ()->countryCityService.deleteCountryById(0));
        Assert.assertEquals("Country not found with ID : 0", exception.getMessage());
    }
    @Test
    public void delete_country_if_country_object_is_given() throws Exception {
        Country country =  new Country(
                1,
                "South Africa",
                "RSA");
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));
        Optional<Country> countryById = countryCityService.findByCountryId(country.getCountry_Id());
        countryCityService.deleteCountryById(countryById.get().getCountry_Id());
        verify(countryRepository, times(1)).deleteById(country.getCountry_Id());
    }
}
