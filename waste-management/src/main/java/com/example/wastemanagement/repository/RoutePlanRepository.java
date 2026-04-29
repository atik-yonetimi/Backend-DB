package com.example.wastemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // 🚨 EKSİK OLAN İMPORT
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; // 🚨 EKSİK OLAN İMPORT

import com.example.wastemanagement.entity.RoutePlan; // 🚨 EKSİK OLAN İMPORT
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.WasteType;

@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, Long> {

    List<RoutePlan> findByStatus(RouteStatus status);

    List<RoutePlan> findByVehicleIdAndStatus(Long vehicleId, RouteStatus status);

    // 🚨 GÜNCELLENEN KISIM: Benden BAŞKA bu atık türünde aktif araç var mı kontrolü
    @Query("SELECT COUNT(r) > 0 FROM RoutePlan r JOIN Vehicle v ON r.vehicleId = v.id WHERE v.wasteType = :wasteType AND r.status = 'ACTIVE' AND v.id != :vehicleId")
    boolean existsOtherActiveRouteForWasteType(@Param("wasteType") WasteType wasteType, @Param("vehicleId") Long vehicleId);
}