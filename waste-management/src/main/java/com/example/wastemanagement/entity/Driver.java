package com.example.wastemanagement.entity;

import java.time.OffsetDateTime;

public class Driver {

    private Long id;
    private String plateLogin;
    private Long assignedVehicleId;
    private OffsetDateTime createdAt;

    public Driver() {
    }

    public Driver(Long id, String plateLogin, Long assignedVehicleId, OffsetDateTime createdAt) {
        this.id = id;
        this.plateLogin = plateLogin;
        this.assignedVehicleId = assignedVehicleId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateLogin() {
        return plateLogin;
    }

    public void setPlateLogin(String plateLogin) {
        this.plateLogin = plateLogin;
    }

    public Long getAssignedVehicleId() {
        return assignedVehicleId;
    }

    public void setAssignedVehicleId(Long assignedVehicleId) {
        this.assignedVehicleId = assignedVehicleId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}