package com.renaissance.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.*;
//import java.util.Set;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private long id;
    @NotBlank(message = "Address is required")
    @Size(min=3, max= 30, message = "Address must be from 3 to 100 characters")
    @Column(name="street_address")
    private String streetAddress;
    @NotBlank(message = "Postal Code is required")
    @Size(min=3, max= 30, message = "Postal code must be from 3 to 30 characters")
    @Column(name="postalcode")
    private String postalCode;

    @ManyToOne(targetEntity = City.class, optional = false)
    @JoinColumn(name="city_id", nullable = false)
    @NotNull(message = "City is required")
    private City city;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_address",
            joinColumns = @JoinColumn(name = "emp_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Role> empAddress = new HashSet<>();
}
