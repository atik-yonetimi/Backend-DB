package com.example.wastemanagement.dto.telemetry;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class TelemetryItemRequest {

    @NotNull
    private Long containerId;

    @Min(0)
    @Max(100)
    private double fillPercent;

    private Double lat;
    private Double lng;

    @NotNull
    private OffsetDateTime recordedAt;

    public TelemetryItemRequest() {
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public double getFillPercent() {
        return fillPercent;
    }

    public void setFillPercent(double fillPercent) {
        this.fillPercent = fillPercent;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public OffsetDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(OffsetDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}