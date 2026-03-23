package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.WasteType;

public class Vehicle {
    private String id;
    private String plate;
    private WasteType wasteType;
    private double garageLat;
    private double garageLng;
    private boolean active;

    public Vehicle() {
    }

    public Vehicle(String id, String plate, WasteType wasteType, double garageLat, double garageLng, boolean active) {
        this.id = id;
        this.plate = plate;
        this.wasteType = wasteType;
        this.garageLat = garageLat;
        this.garageLng = garageLng;
        this.active = active;
    }

    public String getId() {
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

    public boolean isActive() {
        return active;
    }

    public void setId(String id) {
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

    public void setActive(boolean active) {
        this.active = active;
    }
}