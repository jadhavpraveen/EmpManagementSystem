package com.renaissance.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="country_id")
    private int id;
    @Column(name="country_name")
    @NotBlank(message = "Country name is required")
    @Size(min=3, max= 30, message = "Country name must be from 3 to 30 characters")
    private String countryName;
    @Column(name="country_code")
    @NotBlank(message = "Country Code is required")
    @Size(min=3, max= 5, message = "Country code must be from 3 to 5 characters")
    private String countryCode;

    public Country(int country_Id, String countryName, String countryCode) {
        this.id = country_Id;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }
    public Country()  {

    }
    public int getCountry_Id() {
        return id;
    }

    public void setCountry_Id(int country_Id) {
        this.id = country_Id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
