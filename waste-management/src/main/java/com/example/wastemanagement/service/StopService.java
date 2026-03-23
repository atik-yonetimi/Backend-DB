package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.stop.StopStatusUpdateRequest;
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

@Service
public class StopService {

    private final InMemoryStore store;

    public StopService(InMemoryStore store) {
        this.store = store;
    }

    public RouteStop updateStopStatus(String stopId, StopStatusUpdateRequest request, Driver currentDriver) {
        RouteStop stop = store.getRouteStops().get(stopId);
        if (stop == null) {
            throw new NotFoundException("Durak bulunamadi");
        }

        RoutePlan routePlan = validateStopAccess(stop, currentDriver);

        if (routePlan.getStatus() != RouteStatus.ACTIVE) {
            throw new IllegalStateException("Aktif olmayan rotada guncelleme yapilamaz");
        }

        if (request.getStatus() == StopStatus.PENDING) {
            throw new IllegalArgumentException("Durak pending durumuna geri alinamaz");
        }

        if (request.getStatus() == StopStatus.ARRIVED) {
            if (stop.getStatus() != StopStatus.PENDING) {
                throw new IllegalStateException("Sadece pending durak arrived olabilir");
            }
            stop.setStatus(StopStatus.ARRIVED);
            stop.setArrivalAt(OffsetDateTime.now());
        }

        if (request.getStatus() == StopStatus.DONE) {
            boolean collectionExists = store.getCollections().values().stream()
                    .map(CollectionRecord::getRouteStopId)
                    .anyMatch(id -> id.equals(stop.getId()));

            if (!collectionExists) {
                throw new IllegalStateException("Done icin once collection kaydi olusturulmalidir");
            }

            stop.setStatus(StopStatus.DONE);
            stop.setCompletedAt(OffsetDateTime.now());
        }

        if (request.getStatus() == StopStatus.SKIPPED) {
            if (request.getSkipReason() == null || request.getSkipReason().isBlank()) {
                throw new IllegalArgumentException("Skipped icin skipReason zorunludur");
            }

            stop.setStatus(StopStatus.SKIPPED);
            stop.setSkipReason(request.getSkipReason());
            stop.setSkippedAt(OffsetDateTime.now());
        }

        return stop;
    }

    public RoutePlan validateStopAccess(RouteStop stop, Driver currentDriver) {
        RoutePlan routePlan = store.getRoutePlans().get(stop.getRoutePlanId());
        if (routePlan == null) {
            throw new NotFoundException("Rota bulunamadi");
        }

        if (!routePlan.getVehicleId().equals(currentDriver.getVehicleId())) {
            throw new IllegalArgumentException("Bu duraga erisim yetkiniz yok");
        }

        return routePlan;
    }
}