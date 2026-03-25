package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.WasteType;
import java.math.BigDecimal;

import java.time.OffsetDateTime;

public class LatestState {
    private Long containerId;
    private WasteType wasteType;
    private BigDecimal fillPercent;
    private double lat;
    private double lng;
    private OffsetDateTime sourceTimestamp;
    private OffsetDateTime ingestedAt;

    public LatestState() {
    }

    public LatestState(Long containerId, WasteType wasteType, BigDecimal fillPercent,
                       double lat, double lng, OffsetDateTime sourceTimestamp, 
                       OffsetDateTime ingestedAt) {
        this.containerId = containerId;
        this.wasteType = wasteType;
        this.fillPercent = fillPercent;
        this.lat = lat;
        this.lng = lng;
        this.sourceTimestamp= sourceTimestamp;
        this.ingestedAt= ingestedAt;
    }

    public Long getContainerId() {
        return containerId;
    }

    public WasteType getWasteType() {
        return wasteType;
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

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
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