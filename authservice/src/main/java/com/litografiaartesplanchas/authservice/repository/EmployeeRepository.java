package com.litografiaartesplanchas.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.authservice.model.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Integer>{
    Optional<Employee> findByEmail(String email);
}
