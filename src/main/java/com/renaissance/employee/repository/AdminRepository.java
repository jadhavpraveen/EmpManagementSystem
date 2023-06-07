package com.renaissance.employee.repository;

import com.renaissance.employee.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.login = :login and a.password= :password")
    public Admin verifyAdmin(@Param("login") String login, @Param("password") String password);

}
