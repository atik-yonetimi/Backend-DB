package com.example.wastemanagement.dto.collection;

import java.time.OffsetDateTime;

public class CollectionResponse {

    private Long id;
    private Long routeStopId;
    private Long vehicleId;
    private Long driverId;
    private String result;
    private double collectedKg;
    private String skipReason;
    private OffsetDateTime collectedAt;
    private String note;

    public CollectionResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getRouteStopId() {
        return routeStopId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public String getResult() {
        return result;
    }

    public double getCollectedKg() {
        return collectedKg;
    }

    public String getSkipReason() {
        return skipReason;
    }

    public OffsetDateTime getCollectedAt() {
        return collectedAt;
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

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setCollectedKg(double collectedKg) {
        this.collectedKg = collectedKg;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }

    public void setCollectedAt(OffsetDateTime collectedAt) {
        this.collectedAt = collectedAt;
    }

    public void setNote(String note) {
        this.note = note;
    }
}