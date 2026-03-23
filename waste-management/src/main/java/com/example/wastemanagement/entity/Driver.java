package com.example.wastemanagement.entity;

public class Driver {
    private String id;
    private String fullName;
    private String vehicleId;
    private boolean active;

    public Driver() {
    }

    public Driver(String id, String fullName, String vehicleId, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.vehicleId = vehicleId;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}