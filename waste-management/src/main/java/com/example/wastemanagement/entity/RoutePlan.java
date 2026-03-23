package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.TriggerMode;
import com.example.wastemanagement.enums.WasteType;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoutePlan {
    private String id;
    private String vehicleId;
    private WasteType wasteType;
    private RouteStatus status;
    private int versionNo;
    private OffsetDateTime generatedAt;
    private TriggerMode generationMode;
    private List<String> stopIds = new ArrayList<>();

    public RoutePlan() {
    }

    public RoutePlan(String id, String vehicleId, WasteType wasteType, RouteStatus status,
                     int versionNo, OffsetDateTime generatedAt, TriggerMode generationMode) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.wasteType = wasteType;
        this.status = status;
        this.versionNo = versionNo;
        this.generatedAt = generatedAt;
        this.generationMode = generationMode;
    }

    public String getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public OffsetDateTime getGeneratedAt() {
        return generatedAt;
    }

    public TriggerMode getGenerationMode() {
        return generationMode;
    }

    public List<String> getStopIds() {
        return stopIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public void setGeneratedAt(OffsetDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setGenerationMode(TriggerMode generationMode) {
        this.generationMode = generationMode;
    }

    public void setStopIds(List<String> stopIds) {
        this.stopIds = stopIds;
    }
}