package com.example.wastemanagement.dto.stop;

import com.example.wastemanagement.enums.StopStatus;
import jakarta.validation.constraints.NotNull;

public class StopStatusUpdateRequest {

    @NotNull
    private StopStatus status;

    private String skipReason;

    public StopStatusUpdateRequest() {
    }

    public StopStatus getStatus() {
        return status;
    }

    public void setStatus(StopStatus status) {
        this.status = status;
    }

    public String getSkipReason() {
        return skipReason;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }
}