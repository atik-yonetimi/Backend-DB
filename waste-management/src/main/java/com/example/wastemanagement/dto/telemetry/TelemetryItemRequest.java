package com.example.wastemanagement.dto.telemetry;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TelemetryItemRequest {

    @NotBlank
    private String containerId;

    @Min(0)
    @Max(100)
    private int fillLevelPercent;

    private double lat;
    private double lng;

    @NotNull
    private OffsetDateTime recordedAt;

    public TelemetryItemRequest() {
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public int getFillLevelPercent() {
        return fillLevelPercent;
    }

    public void setFillLevelPercent(int fillLevelPercent) {
        this.fillLevelPercent = fillLevelPercent;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public OffsetDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(OffsetDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}