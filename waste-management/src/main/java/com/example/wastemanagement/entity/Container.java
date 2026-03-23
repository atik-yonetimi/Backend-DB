package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.WasteType;

public class Container {
    private String id;
    private String containerCode;
    private WasteType wasteType;
    private double lat;
    private double lng;
    private boolean active;

    public Container() {
    }

    public Container(String id, String containerCode, WasteType wasteType, double lat, double lng, boolean active) {
        this.id = id;
        this.containerCode = containerCode;
        this.wasteType = wasteType;
        this.lat = lat;
        this.lng = lng;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getContainerCode() {
        return containerCode;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}