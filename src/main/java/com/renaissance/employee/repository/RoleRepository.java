package com.renaissance.employee.repository;

import com.renaissance.employee.model.Country;
import com.renaissance.employee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where lower(r.roleName) = lower(:roleName)")
    Role findRoleByName(@Param("roleName") String roleName);
}
