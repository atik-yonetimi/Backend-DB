package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.WasteType; // 🚨 EKSİK OLAN İMPORT
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // 🚨 EKSİK OLAN İMPORT
import org.springframework.data.repository.query.Param; // 🚨 EKSİK OLAN İMPORT
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, Long> {

    List<RoutePlan> findByStatus(RouteStatus status);
    
    List<RoutePlan> findByVehicleIdAndStatus(Long vehicleId, RouteStatus status);

    // 🚨 YENİ EKLENEN KISIM: Bu atık türünde aktif bir araç var mı kontrolü
    @Query("SELECT COUNT(r) > 0 FROM RoutePlan r JOIN Vehicle v ON r.vehicleId = v.id WHERE v.wasteType = :wasteType AND r.status = 'ACTIVE'")
    boolean existsActiveRouteForWasteType(@Param("wasteType") WasteType wasteType);
}