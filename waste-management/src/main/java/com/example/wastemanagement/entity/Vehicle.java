package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.WasteType;
import java.time.OffsetDateTime;

public class Vehicle {
    private Long id;
    private String plate;
    private WasteType wasteType;
    private double garageLat;
    private double garageLng;
    private OffsetDateTime createdAt;

    public Vehicle() {
    }

    public Vehicle(Long id, String plate, WasteType wasteType, double garageLat, double garageLng, OffsetDateTime createdAt) {
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

    public double getGarageLat() {
        return garageLat;
    }

    public double getGarageLng() {
        return garageLng;
    }

    public OffsetDateTime getcreatedAt() {
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

    public void setGarageLat(double garageLat) {
        this.garageLat = garageLat;
    }

    public void setGarageLng(double garageLng) {
        this.garageLng = garageLng;
    }

    public void setCreateAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}