package com.example.wastemanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "telemetry")
public class TelemetryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id")
    private Long containerId;

    @Column(name = "fill_percent")
    private BigDecimal fillPercent;

    private Double lat;

    private Double lng;

    @Column(name = "source_timestamp")
    private OffsetDateTime sourceTimestamp;

    @Column(name = "ingested_at")
    private OffsetDateTime ingestedAt;

    public TelemetryRecord() {
    }

    public TelemetryRecord(Long id,
                           Long containerId,
                           BigDecimal fillPercent,
                           Double lat,
                           Double lng,
                           OffsetDateTime sourceTimestamp,
                           OffsetDateTime ingestedAt) {
        this.id = id;
        this.containerId = containerId;
        this.fillPercent = fillPercent;
        this.lat = lat;
        this.lng = lng;
        this.sourceTimestamp = sourceTimestamp;
        this.ingestedAt = ingestedAt;
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

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
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

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setSourceTimestamp(OffsetDateTime sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public void setIngestedAt(OffsetDateTime ingestedAt) {
        this.ingestedAt = ingestedAt;
    }
}