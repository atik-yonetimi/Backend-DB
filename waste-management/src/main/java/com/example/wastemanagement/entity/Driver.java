package com.example.wastemanagement.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🚨 BUNU DA EKLE
    private Long id;

    @Column(name = "plate_login")
    private String plateLogin;

    @Column(name = "assigned_vehicle_id")
    private Long assignedVehicleId;

    @Column(name = "created_at")
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

    public String getPlateLogin() {
        return plateLogin;
    }

    public Long getAssignedVehicleId() {
        return assignedVehicleId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlateLogin(String plateLogin) {
        this.plateLogin = plateLogin;
    }

    public void setAssignedVehicleId(Long assignedVehicleId) {
        this.assignedVehicleId = assignedVehicleId;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}