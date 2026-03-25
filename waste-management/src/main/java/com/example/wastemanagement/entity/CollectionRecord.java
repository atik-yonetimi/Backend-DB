package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.CollectionResult;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class CollectionRecord {

    private Long id;
    private Long routeStopId;
    private Long driverId;
    private Long vehicleId;
    private CollectionResult result;
    private BigDecimal amountKg;
    private String skipReason;
    private OffsetDateTime collectedAt;
    private Double gpsLat;
    private Double gpsLng;
    private String idempotencyKey;
    private OffsetDateTime createdAt;
    private String note;

    public CollectionRecord() {
    }

    public CollectionRecord(Long id,
                            Long routeStopId,
                            Long driverId,
                            Long vehicleId,
                            CollectionResult result,
                            BigDecimal amountKg,
                            String skipReason,
                            OffsetDateTime collectedAt,
                            Double gpsLat,
                            Double gpsLng,
                            String idempotencyKey,
                            OffsetDateTime createdAt) {
        this.id = id;
        this.routeStopId = routeStopId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.result = result;
        this.amountKg = amountKg;
        this.skipReason = skipReason;
        this.collectedAt = collectedAt;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getRouteStopId() {
        return routeStopId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public CollectionResult getResult() {
        return result;
    }

    public BigDecimal getAmountKg() {
        return amountKg;
    }

    public String getSkipReason() {
        return skipReason;
    }

    public OffsetDateTime getCollectedAt() {
        return collectedAt;
    }

    public Double getGpsLat() {
        return gpsLat;
    }

    public Double getGpsLng() {
        return gpsLng;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRouteStopId(Long routeStopId) {
        this.routeStopId = routeStopId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setResult(CollectionResult result) {
        this.result = result;
    }

    public void setAmountKg(BigDecimal amountKg) {
        this.amountKg = amountKg;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }

    public void setCollectedAt(OffsetDateTime collectedAt) {
        this.collectedAt = collectedAt;
    }

    public void setGpsLat(Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public void setGpsLng(Double gpsLng) {
        this.gpsLng = gpsLng;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setNote(String note) {
        this.note = note;
    }
}