package com.renaissance.employee.repository;

import com.renaissance.employee.model.City;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("select c from City c where c.country.id = :countryId")
    List<City> findByCountryId(@Param("countryId") int countryId);

   @Query("select c from City c where lower(c.cityName) = lower(:cityName)")
   Optional<City> findCityByCityName(@Param("cityName") String cityName);
   //Optional<City> findByCityName(String cityName);

    @Transactional
    @Modifying // to mark update or delete query
    @Query(value = "delete from City c where country_id = :countryId", nativeQuery = true)
    void deleteCityByCountryId(@Param("countryId") int countryId);

}
