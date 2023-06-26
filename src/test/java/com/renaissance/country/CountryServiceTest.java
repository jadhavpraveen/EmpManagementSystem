package com.renaissance.country;


import com.renaissance.employee.exception.NullException;
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
import static org.mockito.Mockito.when;

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
//        Country country =  new Country(
//                1,
//                "South Africa",
//                "RSA");
        NullException exception = Assert.assertThrows(
                "Object to be inserted cannot be null",
                NullException.class,
                ()->countryCityService.addCountry(null));
    }
    @Test
    public void should_return_no_country_if_countrylist_is_empty()  throws Exception  {
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
}
