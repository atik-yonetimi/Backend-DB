package com.example.wastemanagement.dto.route;

import java.time.OffsetDateTime;
import java.util.List;

public class RoutePlanResponse {

    private String id;
    private String vehicleId;
    private String wasteType;
    private String status;
    private int versionNo;
    private OffsetDateTime generatedAt;
    private List<StopResponse> stops;

    public RoutePlanResponse() {
    }

    public String getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getWasteType() {
        return wasteType;
    }

    public String getStatus() {
        return status;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public OffsetDateTime getGeneratedAt() {
        return generatedAt;
    }

    public List<StopResponse> getStops() {
        return stops;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public void setGeneratedAt(OffsetDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setStops(List<StopResponse> stops) {
        this.stops = stops;
    }
}