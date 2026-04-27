package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wastemanagement.enums.StopStatus;

import java.util.List;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {
    int countByRoutePlanId(Long routePlanId);

    int countByRoutePlanIdAndStatus(Long routePlanId, StopStatus status);
    List<RouteStop> findByRoutePlanIdOrderBySequenceNoAsc(Long routePlanId);
    
    
}