package com.example.wastemanagement.dto.stop;

import com.example.wastemanagement.enums.StopStatus;
import jakarta.validation.constraints.NotNull;

public class StopStatusUpdateRequest {

    @NotNull
    private StopStatus status;

    public StopStatusUpdateRequest() {
    }

    public StopStatus getStatus() {
        return status;
    }

    public void setStatus(StopStatus status) {
        this.status = status;
    }
}