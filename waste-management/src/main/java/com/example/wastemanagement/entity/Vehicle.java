package com.example.wastemanagement.entity;

import java.time.OffsetDateTime;

import com.example.wastemanagement.enums.WasteType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🚨 İŞTE BU SATIRI EKLEMELİSİN
    private Long id;

    private String plate;

    @Enumerated(EnumType.STRING)
    @Column(name = "waste_type")
    private WasteType wasteType;

    @Column(name = "garage_lat")
    private Double garageLat;

    @Column(name = "garage_lng")
    private Double garageLng;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "login_password")
    private String loginPassword;

    public Vehicle() {
    }

    public Vehicle(Long id, String plate, WasteType wasteType, Double garageLat, Double garageLng, OffsetDateTime createdAt) {
        this.id = id;
        this.plate = plate;
        this.wasteType = wasteType;
        this.garageLat = garageLat;
        this.garageLng = garageLng;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public Double getGarageLat() {
        return garageLat;
    }

    public Double getGarageLng() {
        return garageLng;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public void setGarageLat(Double garageLat) {
        this.garageLat = garageLat;
    }

    public void setGarageLng(Double garageLng) {
        this.garageLng = garageLng;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}