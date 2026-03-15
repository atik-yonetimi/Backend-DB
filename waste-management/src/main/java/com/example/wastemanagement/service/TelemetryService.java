package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.telemetry.TelemetryItemRequest;
import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.LatestState;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.InMemoryStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
                throw new NotFoundException("Container bulunamadı: " + item.getContainerId());
            }

            TelemetryRecord record = new TelemetryRecord(
                    "tel_" + UUID.randomUUID().toString().substring(0, 8),
                    item.getContainerId(),
                    item.getFillLevelPercent(),
                    item.getLat(),
                    item.getLng(),
                    item.getRecordedAt()
            );

            store.getTelemetryRecords().put(record.getId(), record);

            LatestState latestState = new LatestState(
                    item.getContainerId(),
                    container.getWasteType(),
                    item.getFillLevelPercent(),
                    item.getLat(),
                    item.getLng(),
                    item.getRecordedAt()
            );

            store.getLatestStates().put(item.getContainerId(), latestState);
            acceptedCount++;
        }

        return acceptedCount;
    }
}