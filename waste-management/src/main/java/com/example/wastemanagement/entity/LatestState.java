package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.WasteType;

import java.time.OffsetDateTime;

public class LatestState {
    private String containerId;
    private WasteType wasteType;
    private int fillLevelPercent;
    private double lat;
    private double lng;
    private OffsetDateTime lastTelemetryAt;

    public LatestState() {
    }

    public LatestState(String containerId, WasteType wasteType, int fillLevelPercent,
                       double lat, double lng, OffsetDateTime lastTelemetryAt) {
        this.containerId = containerId;
        this.wasteType = wasteType;
        this.fillLevelPercent = fillLevelPercent;
        this.lat = lat;
        this.lng = lng;
        this.lastTelemetryAt = lastTelemetryAt;
    }

    public String getContainerId() {
        return containerId;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public int getFillLevelPercent() {
        return fillLevelPercent;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public OffsetDateTime getLastTelemetryAt() {
        return lastTelemetryAt;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public void setFillLevelPercent(int fillLevelPercent) {
        this.fillLevelPercent = fillLevelPercent;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLastTelemetryAt(OffsetDateTime lastTelemetryAt) {
        this.lastTelemetryAt = lastTelemetryAt;
    }
}