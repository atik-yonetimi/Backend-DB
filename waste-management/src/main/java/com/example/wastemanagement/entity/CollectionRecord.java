package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.CollectionResult;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "collections")
public class CollectionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_stop_id")
    private Long routeStopId;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    private CollectionResult result;

    @Column(name = "amount_kg")
    private BigDecimal amountKg;

    @Column(name = "skip_reason")
    private String skipReason;

    @Column(name = "collected_at")
    private OffsetDateTime collectedAt;

    @Column(name = "gps_lat")
    private Double gpsLat;

    @Column(name = "gps_lng")
    private Double gpsLng;

    @Column(name = "idempotency_key")
    private String idempotencyKey;

    @Column(name = "created_at")
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