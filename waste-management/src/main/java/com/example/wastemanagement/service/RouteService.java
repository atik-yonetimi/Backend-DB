package com.example.wastemanagement.service;

import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.repository.ContainerRepository;
import com.example.wastemanagement.config.AppConstants;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.enums.TriggerMode;
import com.example.wastemanagement.enums.WasteType;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.RoutePlanRepository;
import com.example.wastemanagement.repository.RouteStopRepository;
import com.example.wastemanagement.repository.TelemetryRepository;
import com.example.wastemanagement.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
public class RouteService {

    private final VehicleRepository vehicleRepository;
    private final RoutePlanRepository routePlanRepository;
    private final RouteStopRepository routeStopRepository;
    private final TelemetryRepository telemetryRepository;
    private final ContainerRepository containerRepository;

    public RouteService(VehicleRepository vehicleRepository,
                    RoutePlanRepository routePlanRepository,
                    RouteStopRepository routeStopRepository,
                    TelemetryRepository telemetryRepository,
                    ContainerRepository containerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.routePlanRepository = routePlanRepository;
        this.routeStopRepository = routeStopRepository;
        this.telemetryRepository = telemetryRepository;
        this.containerRepository = containerRepository;
    }

    public RoutePlan generateRoute(WasteType wasteType, TriggerMode generationMode) {
        Vehicle vehicle = vehicleRepository.findAll().stream()
                .filter(v -> v.getWasteType() == wasteType)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Bu atik turu icin arac bulunamadi"));

        RoutePlan previousActive = getActiveRouteForVehicle(vehicle.getId());
        if (previousActive != null) {
            previousActive.setStatus(RouteStatus.CANCELLED);
            routePlanRepository.save(previousActive);
        }

        List<TelemetryRecord> candidates = selectCandidates();

        List<TelemetryRecord> filteredByWasteType = candidates.stream()
            .filter(t -> {
                Container container = containerRepository.findById(t.getContainerId())
                    .orElse(null);

                return container != null && container.getWasteType() == vehicle.getWasteType();
            })
            .collect(Collectors.toList());

        List<TelemetryRecord> ordered = nearestNeighborOrder(vehicle, filteredByWasteType);

        RoutePlan routePlan = new RoutePlan(
                null,
                vehicle.getId(),
                wasteType,
                RouteStatus.ACTIVE,
                OffsetDateTime.now()
        );

        routePlan = routePlanRepository.save(routePlan);

        int sequence = 1;
        for (TelemetryRecord telemetry : ordered) {
            RouteStop stop = new RouteStop(
                    null,
                    routePlan.getId(),
                    telemetry.getContainerId(),
                    sequence++,
                    StopStatus.PENDING,
                    OffsetDateTime.now()
            );

            routeStopRepository.save(stop);
        }

        return routePlan;
    }

    public RoutePlan getActiveRouteForVehicle(Long vehicleId) {
        return routePlanRepository.findAll().stream()
            .filter(r -> r.getVehicleId().equals(vehicleId))
            .filter(r -> r.getStatus() == RouteStatus.ACTIVE)
            .max(java.util.Comparator.comparing(RoutePlan::getCreatedAt))
            .orElse(null);
}

    private List<TelemetryRecord> selectCandidates() {
        List<TelemetryRecord> allTelemetry = telemetryRepository.findAll();

        Map<Long, TelemetryRecord> latestByContainer = allTelemetry.stream()
                .collect(Collectors.toMap(
                        TelemetryRecord::getContainerId,
                        t -> t,
                        (t1, t2) -> t1.getSourceTimestamp().isAfter(t2.getSourceTimestamp()) ? t1 : t2
                ));

        return latestByContainer.values().stream()
                .filter(t -> t.getFillPercent().compareTo(BigDecimal.valueOf(AppConstants.THRESHOLD_PERCENT)) >= 0)
                .collect(Collectors.toList());
    }

    private List<TelemetryRecord> nearestNeighborOrder(Vehicle vehicle, List<TelemetryRecord> telemetryList) {
        List<TelemetryRecord> remaining = new ArrayList<>(telemetryList);
        List<TelemetryRecord> ordered = new ArrayList<>();

        double currentLat = vehicle.getGarageLat();
        double currentLng = vehicle.getGarageLng();

        while (!remaining.isEmpty()) {
            final double refLat = currentLat;
            final double refLng = currentLng;

            TelemetryRecord nearest = remaining.stream()
                    .min(Comparator.comparingDouble(t -> distance(refLat, refLng, t.getLat(), t.getLng())))
                    .orElseThrow();

            ordered.add(nearest);
            remaining.remove(nearest);

            currentLat = nearest.getLat();
            currentLng = nearest.getLng();
        }

        return ordered;
    }

    private double distance(Double aLat, Double aLng, Double bLat, Double bLng) {
        return Math.sqrt(Math.pow(aLat - bLat, 2) + Math.pow(aLng - bLng, 2));
    }
}