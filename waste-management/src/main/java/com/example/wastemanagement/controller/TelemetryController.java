package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.telemetry.TelemetryBatchRequest;
import com.example.wastemanagement.dto.telemetry.TelemetryBatchResponse;
import com.example.wastemanagement.service.TelemetryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @PostMapping("/ingest")
    public TelemetryBatchResponse ingest(@Valid @RequestBody TelemetryBatchRequest request) {
        int acceptedCount = telemetryService.ingestBatch(request.getItems());
        return new TelemetryBatchResponse(acceptedCount);
    }
}