package com.example.wastemanagement.dto.telemetry;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class TelemetryBatchRequest {

    @Valid
    @NotEmpty
    private List<TelemetryItemRequest> items;

    public TelemetryBatchRequest() {
    }

    public List<TelemetryItemRequest> getItems() {
        return items;
    }

    public void setItems(List<TelemetryItemRequest> items) {
        this.items = items;
    }
}