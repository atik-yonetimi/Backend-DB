package com.example.wastemanagement.dto.telemetry;

public class TelemetryBatchResponse {

    private int acceptedCount;

    public TelemetryBatchResponse() {
    }

    public TelemetryBatchResponse(int acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(int acceptedCount) {
        this.acceptedCount = acceptedCount;
    }
}