package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.WasteType;

import java.time.OffsetDateTime;

public class Container {

    private Long id;
    private WasteType wasteType;
    private Double lat;
    private Double lng;
    private String status;
    private OffsetDateTime createdAt;

    public Container() {
    }

    public Container(Long id, WasteType wasteType, Double lat, Double lng, String status, OffsetDateTime createdAt) {
        this.id = id;
        this.wasteType = wasteType;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}