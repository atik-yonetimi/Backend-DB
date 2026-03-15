package com.example.wastemanagement.dto.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CollectionCreateRequest {

    @NotBlank
    private String routeStopId;

    @Positive
    private double collectedKg;

    private String note;

    public CollectionCreateRequest() {
    }

    public String getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(String routeStopId) {
        this.routeStopId = routeStopId;
    }

    public double getCollectedKg() {
        return collectedKg;
    }

    public void setCollectedKg(double collectedKg) {
        this.collectedKg = collectedKg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}