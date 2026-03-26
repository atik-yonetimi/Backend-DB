package com.example.wastemanagement.service;

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
import com.example.wastemanagement.repository.CollectionRepository;
import com.example.wastemanagement.repository.RouteStopRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class CollectionService {

    private final RouteStopRepository routeStopRepository;
    private final CollectionRepository collectionRepository;
    private final StopService stopService;

    public CollectionService(RouteStopRepository routeStopRepository,
                             CollectionRepository collectionRepository,
                             StopService stopService) {
        this.routeStopRepository = routeStopRepository;
        this.collectionRepository = collectionRepository;
        this.stopService = stopService;
    }

    public CollectionResponse createCollection(CollectionCreateRequest request, Driver currentDriver) {
        RouteStop stop = routeStopRepository.findById(request.getRouteStopId())
                .orElseThrow(() -> new NotFoundException("Durak bulunamadi"));

        RoutePlan routePlan = stopService.validateStopAccess(stop, currentDriver);

        if (routePlan.getStatus() != RouteStatus.ACTIVE) {
            throw new IllegalStateException("Aktif olmayan rota icin collection eklenemez");
        }

        if (stop.getStatus() == StopStatus.SKIPPED) {
            throw new IllegalStateException("Skipped durak icin collection eklenemez");
        }

        boolean exists = collectionRepository.existsByRouteStopId(stop.getId());
        if (exists) {
            throw new IllegalStateException("Bu durak icin collection zaten var");
        }

        OffsetDateTime now = OffsetDateTime.now();

        CollectionRecord record = new CollectionRecord(
                null,
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

        record.setNote(request.getNote());

        record = collectionRepository.save(record);

        stop.setStatus(StopStatus.DONE);
        routeStopRepository.save(stop);

        CollectionResponse response = new CollectionResponse();
        response.setId(record.getId());
        response.setRouteStopId(record.getRouteStopId());
        response.setVehicleId(record.getVehicleId());
        response.setDriverId(record.getDriverId());
        response.setResult(record.getResult().name());
        response.setCollectedKg(record.getAmountKg().doubleValue());
        response.setSkipReason(record.getSkipReason());
        response.setCollectedAt(record.getCollectedAt());
        response.setNote(record.getNote());

        return response;
    }
}