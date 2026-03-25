package com.example.wastemanagement.service;

import com.example.wastemanagement.repository.InMemoryStore;
import com.example.wastemanagement.dto.collection.CollectionCreateRequest;
import com.example.wastemanagement.dto.collection.CollectionResponse;
import com.example.wastemanagement.entity.CollectionRecord;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.enums.CollectionResult;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class CollectionService {

    private final InMemoryStore store;
    private final StopService stopService;

    public CollectionService(InMemoryStore store, StopService stopService) {
        this.store = store;
        this.stopService = stopService;
    }

    public CollectionResponse createCollection(CollectionCreateRequest request, Driver currentDriver) {
        RouteStop stop = store.getRouteStops().get(request.getRouteStopId());
        if (stop == null) {
            throw new NotFoundException("Durak bulunamadi");
        }

        RoutePlan routePlan = stopService.validateStopAccess(stop, currentDriver);

        if (routePlan.getStatus() != RouteStatus.ACTIVE) {
            throw new IllegalStateException("Aktif olmayan rota icin collection eklenemez");
        }

        if (stop.getStatus() == StopStatus.SKIPPED) {
            throw new IllegalStateException("Skipped durak icin collection eklenemez");
        }

        boolean exists = store.getCollections().values().stream()
                .anyMatch(c -> c.getRouteStopId().equals(stop.getId()));

        if (exists) {
            throw new IllegalStateException("Bu durak icin collection zaten var");
        }

        Long collectionId = (long) (store.getCollections().size() + 1);
        OffsetDateTime now = OffsetDateTime.now();

        CollectionRecord record = new CollectionRecord(
                collectionId,
                stop.getId(),
                currentDriver.getId(),
                routePlan.getVehicleId(),
                CollectionResult.DONE,
                BigDecimal.valueOf(request.getCollectedKg()),
                null,
                now,
                null,
                null,
                null,
                now
        );

        store.getCollections().put(record.getId(), record);

        stop.setStatus(StopStatus.DONE);

        CollectionResponse response = new CollectionResponse();
        response.setId(record.getId());
        response.setRouteStopId(record.getRouteStopId());
        response.setVehicleId(record.getVehicleId());
        response.setDriverId(record.getDriverId());
        response.setCollectedKg(record.getAmountKg().doubleValue());
        response.setCollectedAt(record.getCollectedAt());
        response.setNote(request.getNote());

        return response;
    }
}