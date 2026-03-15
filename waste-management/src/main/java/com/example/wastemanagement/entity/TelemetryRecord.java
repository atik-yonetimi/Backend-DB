package com.example.wastemanagement.entity;

import java.time.OffsetDateTime;

public class TelemetryRecord {
    private String id;
    private String containerId;
    private int fillLevelPercent;
    private double lat;
    private double lng;
    private OffsetDateTime recordedAt;

    public TelemetryRecord() {
    }

    public TelemetryRecord(String id, String containerId, int fillLevelPercent,
                           double lat, double lng, OffsetDateTime recordedAt) {
        this.id = id;
        this.containerId = containerId;
        this.fillLevelPercent = fillLevelPercent;
        this.lat = lat;
        this.lng = lng;
        this.recordedAt = recordedAt;
    }

    public String getId() {
        return id;
    }

    public String getContainerId() {
        return containerId;
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

    public OffsetDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
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

    public void setRecordedAt(OffsetDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}