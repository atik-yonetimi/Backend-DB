package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.stop.StopStatusUpdateRequest;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.CollectionRepository;
import com.example.wastemanagement.repository.RoutePlanRepository;
import com.example.wastemanagement.repository.RouteStopRepository;
import org.springframework.stereotype.Service;

@Service
public class StopService {

    private final RouteStopRepository routeStopRepository;
    private final RoutePlanRepository routePlanRepository;
    private final CollectionRepository collectionRepository;

    public StopService(RouteStopRepository routeStopRepository,
                       RoutePlanRepository routePlanRepository,
                       CollectionRepository collectionRepository) {
        this.routeStopRepository = routeStopRepository;
        this.routePlanRepository = routePlanRepository;
        this.collectionRepository = collectionRepository;
    }

    public RouteStop updateStopStatus(Long stopId, StopStatusUpdateRequest request, Driver currentDriver) {
        RouteStop stop = routeStopRepository.findById(stopId)
                .orElseThrow(() -> new NotFoundException("Durak bulunamadi"));

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
        }

        if (request.getStatus() == StopStatus.DONE) {
            boolean collectionExists = collectionRepository.existsByRouteStopId(stop.getId());

            if (!collectionExists) {
                throw new IllegalStateException("Done icin once collection kaydi olusturulmalidir");
            }

            stop.setStatus(StopStatus.DONE);
        }

        if (request.getStatus() == StopStatus.SKIPPED) {
            stop.setStatus(StopStatus.SKIPPED);
        }

        return routeStopRepository.save(stop);
    }

    public RoutePlan validateStopAccess(RouteStop stop, Driver currentDriver) {
        RoutePlan routePlan = routePlanRepository.findById(stop.getRoutePlanId())
                .orElseThrow(() -> new NotFoundException("Rota bulunamadi"));

        if (!routePlan.getVehicleId().equals(currentDriver.getAssignedVehicleId())) {
            throw new IllegalArgumentException("Bu duraga erisim yetkiniz yok");
        }

        return routePlan;
    }
}