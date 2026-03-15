package com.example.wastemanagement.entity;

import java.time.OffsetDateTime;

public class CollectionRecord {
    private String id;
    private String routeStopId;
    private String containerId;
    private String vehicleId;
    private String driverId;
    private double collectedKg;
    private OffsetDateTime collectedAt;
    private String note;

    public CollectionRecord() {
    }

    public CollectionRecord(String id, String routeStopId, String containerId,
                            String vehicleId, String driverId, double collectedKg,
                            OffsetDateTime collectedAt, String note) {
        this.id = id;
        this.routeStopId = routeStopId;
        this.containerId = containerId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.collectedKg = collectedKg;
        this.collectedAt = collectedAt;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public String getRouteStopId() {
        return routeStopId;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public double getCollectedKg() {
        return collectedKg;
    }

    public OffsetDateTime getCollectedAt() {
        return collectedAt;
    }

    public String getNote() {
        return note;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRouteStopId(String routeStopId) {
        this.routeStopId = routeStopId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setCollectedKg(double collectedKg) {
        this.collectedKg = collectedKg;
    }

    public void setCollectedAt(OffsetDateTime collectedAt) {
        this.collectedAt = collectedAt;
    }

    public void setNote(String note) {
        this.note = note;
    }
}