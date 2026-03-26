package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByPlateLoginIgnoreCase(String plateLogin);
}