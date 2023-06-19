package com.renaissance.employee.repository;

import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.role.id = :roleId")
    List<Employee> findEmployeesByRoleId(@Param("roleId") int roleId);

//    Optional<Employee> findByUserName(@Param("cityName") String cityName);
    Optional<Employee> findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);

    @Transactional
    @Modifying // to mark update or delete query
    @Query(value = "delete from Employee e where role_id = :roleId", nativeQuery = true)
    void deleteEmployeesByRoleId(@Param("roleId") int roleId);
}
