package com.example.wastemanagement.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TelemetryRecord {
    private Long id;
    private Long containerId;
    private BigDecimal fillPercent;
    private double lat;
    private double lng;
    private OffsetDateTime sourceTimestamp;
    private OffsetDateTime ingestedAt;
    

    public TelemetryRecord() {
    }

    public TelemetryRecord(Long id, Long containerId, BigDecimal fillPercent,
                           double lat, double lng, OffsetDateTime sourceTimestamp, OffsetDateTime ingestedAt) {
        this.id = id;
        this.containerId = containerId;
        this.fillPercent = fillPercent;
        this.lat = lat;
        this.lng = lng;
        this.sourceTimestamp = sourceTimestamp;
        this.ingestedAt= ingestedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getContainerId() {
        return containerId;
    }

    public BigDecimal getFillPercent() {
        return fillPercent;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public OffsetDateTime getSourceTimestamp() {
        return sourceTimestamp;
    }
    
    public OffsetDateTime getIngestedAt() {
        return ingestedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public void setFillPercent(BigDecimal fillPercent) {
        this.fillPercent = fillPercent;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setSourceTimestamp(OffsetDateTime sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }
    
    public void setIngestedAt(OffsetDateTime ingestedAt) {
        this.ingestedAt = ingestedAt;
    }
}