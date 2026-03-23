package com.example.wastemanagement.dto.route;

import com.example.wastemanagement.enums.TriggerMode;
import com.example.wastemanagement.enums.WasteType;
import jakarta.validation.constraints.NotNull;

public class GenerateRouteRequest {

    @NotNull
    private WasteType wasteType;

    private TriggerMode generationMode = TriggerMode.MANUAL;

    public GenerateRouteRequest() {
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public TriggerMode getGenerationMode() {
        return generationMode;
    }

    public void setGenerationMode(TriggerMode generationMode) {
        this.generationMode = generationMode;
    }
}