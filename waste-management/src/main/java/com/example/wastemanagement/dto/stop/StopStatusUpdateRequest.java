package com.example.wastemanagement.dto.stop;

import com.example.wastemanagement.enums.StopStatus;
import jakarta.validation.constraints.NotNull;

public class StopStatusUpdateRequest {

    @NotNull
    private StopStatus status;
    private String reason; // 🚨 BUNU EKLE

    public StopStatusUpdateRequest() {
    }

    public StopStatus getStatus() {
        return status;
    }

    public void setStatus(StopStatus status) {
        this.status = status;
    }
    
    // Getter ve Setter'ını da eklemeyi unutma:
    public String getReason() { return reason; 
    }
    
    
    public void setReason(String reason) { this.reason = reason; 
    }
}