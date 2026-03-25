package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.TriggerMode;
import com.example.wastemanagement.enums.WasteType;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoutePlan {
    private Long id;
    private Long vehicleId;
    private WasteType wasteType;
    private RouteStatus status;
    private OffsetDateTime createdAt;

    public RoutePlan() {
    }

    public RoutePlan(Long id, Long vehicleId, WasteType wasteType, RouteStatus status,
                     OffsetDateTime createdAt ) {
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