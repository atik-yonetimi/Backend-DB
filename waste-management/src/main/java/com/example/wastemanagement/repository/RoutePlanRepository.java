package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.RoutePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, Long> {
    
}