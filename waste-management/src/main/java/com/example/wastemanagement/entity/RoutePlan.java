package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.WasteType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.OffsetDateTime;

@Entity
@Table(name = "route_plans")
public class RoutePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "waste_type", columnDefinition = "waste_type_enum")
    private WasteType wasteType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "route_plan_status_enum")
    private RouteStatus status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public RoutePlan() {
    }

    public RoutePlan(Long id, Long vehicleId, WasteType wasteType, RouteStatus status, OffsetDateTime createdAt) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.wasteType = wasteType;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}