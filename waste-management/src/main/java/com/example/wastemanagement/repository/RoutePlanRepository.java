package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.enums.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, Long> {

    List<RoutePlan> findByStatus(RouteStatus status);

    List<RoutePlan> findByVehicleIdAndStatusOrderByCreatedAtDesc(Long vehicleId, RouteStatus status);
}