package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.collection.CollectionCreateRequest;
import com.example.wastemanagement.dto.collection.CollectionResponse;
import com.example.wastemanagement.entity.CollectionRecord;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.InMemoryStore;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

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

        CollectionRecord record = new CollectionRecord(
                "col_" + UUID.randomUUID().toString().substring(0, 8),
                stop.getId(),
                stop.getContainerId(),
                routePlan.getVehicleId(),
                currentDriver.getId(),
                request.getCollectedKg(),
                OffsetDateTime.now(),
                request.getNote()
        );

        store.getCollections().put(record.getId(), record);

        CollectionResponse response = new CollectionResponse();
        response.setId(record.getId());
        response.setRouteStopId(record.getRouteStopId());
        response.setContainerId(record.getContainerId());
        response.setVehicleId(record.getVehicleId());
        response.setDriverId(record.getDriverId());
        response.setCollectedKg(record.getCollectedKg());
        response.setCollectedAt(record.getCollectedAt());
        response.setNote(record.getNote());

        return response;
    }
}