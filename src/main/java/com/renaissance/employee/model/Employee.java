package com.renaissance.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private long id;
    @ManyToOne(targetEntity = Role.class, optional = false)
    @JoinColumn(name="role_id", nullable = false)
    @NotNull(message = "The Role is required")
    private Role role;
    @NotBlank(message = "User name is required")
    @Size(min=3, max= 30, message = "User name must be from 3 to 30 characters")
    @Column(name = "user_name")
    private String userName;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$",
             message = "Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    @Column(name="password")
    private String password;
    @NotBlank(message = "First name is required")
    @Size(min=3, max= 30, message = "First name must be from 3 to 50 characters")
    @Column(name="first_name")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min=3, max= 30, message = "Last name must be from 3 to 50 characters")
    @Column(name="last_name")
    private String lastName;
    @NotBlank(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    @Column(name="email")
    private String email;
    @NotNull(message = "Gender is required")
    //@Size(min= 1, max=1,message = "Gender must contain maximum of 1 character ")
    @Column(name="gender")
    private char gender;
    @NotBlank(message = "Phone number is required")
    @Size(min=3, max= 50, message = "Phone number must be from 5 to 50 characters")
    @Column(name="phone_number")
    private String phoneNumber;
    @Size(max= 30, message = "Gender must contain maximum of 1 character ")
    @Column(name="designation")
    private String designation;
    @Min(value = 1000, message = "The salary must be greater than 1000")
    @Column(name="salary")
    private int salary;
    @Min(value = 21, message = "The age must be equal or greater than 21")
    @Column(name="age")
    private int age;

    public Employee(int id, Role role, String userName, String password, String firstName, String lastName,
                    String email, char gender, String phoneNumber, String designation, int salary, int age) {
        this.id = id;
        this.role = role;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.designation = designation;
        this.salary = salary;
        this.age = age;
    }

    public Employee() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
