package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.telemetry.TelemetryItemRequest;
import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.LatestState;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.InMemoryStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TelemetryService {

    private final InMemoryStore store;

    public TelemetryService(InMemoryStore store) {
        this.store = store;
    }

    public int ingestBatch(List<TelemetryItemRequest> items) {
        int acceptedCount = 0;

        for (TelemetryItemRequest item : items) {
            Container container = store.getContainers().get(item.getContainerId());
            if (container == null) {
                throw new NotFoundException("Container bulunamadi: " + item.getContainerId());
            }

            Long telemetryId = (long) (store.getTelemetryRecords().size() + 1);
            OffsetDateTime ingestedAt = OffsetDateTime.now();

            TelemetryRecord record = new TelemetryRecord(
                    telemetryId,
                    item.getContainerId(),
                    BigDecimal.valueOf(item.getFillPercent()),
                    item.getLat(),
                    item.getLng(),
                    item.getRecordedAt(),
                    ingestedAt
            );

            store.getTelemetryRecords().put(record.getId(), record);

            LatestState latestState = new LatestState(
                    item.getContainerId(),
                    container.getWasteType(),
                    BigDecimal.valueOf(item.getFillPercent()),
                    item.getLat(),
                    item.getLng(),
                    item.getRecordedAt(),
                    ingestedAt
            );

            store.getLatestStates().put(item.getContainerId(), latestState);
            acceptedCount++;
        }

        return acceptedCount;
    }
}