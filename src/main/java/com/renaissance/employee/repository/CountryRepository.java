package com.renaissance.employee.repository;

import com.renaissance.employee.model.Country;
import com.renaissance.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query("select c from Country c where lower(c.countryName) = lower(:countryName)")
    Country findCountryByName(@Param("countryName") String countryName);
}
