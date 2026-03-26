package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.telemetry.TelemetryItemRequest;
import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.ContainerRepository;
import com.example.wastemanagement.repository.TelemetryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TelemetryService {

    private final ContainerRepository containerRepository;
    private final TelemetryRepository telemetryRepository;

    public TelemetryService(ContainerRepository containerRepository,
                            TelemetryRepository telemetryRepository) {
        this.containerRepository = containerRepository;
        this.telemetryRepository = telemetryRepository;
    }

    public int ingestBatch(List<TelemetryItemRequest> items) {
        int acceptedCount = 0;

        for (TelemetryItemRequest item : items) {
            Container container = containerRepository.findById(item.getContainerId())
                    .orElseThrow(() -> new NotFoundException("Container bulunamadi: " + item.getContainerId()));

            OffsetDateTime ingestedAt = OffsetDateTime.now();

            TelemetryRecord record = new TelemetryRecord(
                    null,
                    container.getId(),
                    BigDecimal.valueOf(item.getFillPercent()),
                    item.getLat(),
                    item.getLng(),
                    item.getRecordedAt(),
                    ingestedAt
            );

            telemetryRepository.save(record);
            acceptedCount++;
        }

        return acceptedCount;
    }
}