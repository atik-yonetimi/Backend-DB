package com.example.wastemanagement.dto.route;

import java.time.OffsetDateTime;
import java.util.List;

public class RoutePlanResponse {

    private Long id;
    private Long vehicleId;
    private String wasteType;
    private String status;
    private OffsetDateTime generatedAt;
    private List<StopResponse> stops;

    public RoutePlanResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getWasteType() {
        return wasteType;
    }

    public String getStatus() {
        return status;
    }

    public OffsetDateTime getGeneratedAt() {
        return generatedAt;
    }

    public List<StopResponse> getStops() {
        return stops;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGeneratedAt(OffsetDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setStops(List<StopResponse> stops) {
        this.stops = stops;
    }
}