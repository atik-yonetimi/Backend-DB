package com.example.wastemanagement.service;

import com.example.wastemanagement.config.AppConstants;
import com.example.wastemanagement.entity.LatestState;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.enums.TriggerMode;
import com.example.wastemanagement.enums.WasteType;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.InMemoryStore;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final InMemoryStore store;

    public RouteService(InMemoryStore store) {
        this.store = store;
    }

    public RoutePlan generateRoute(WasteType wasteType, TriggerMode generationMode) {
        Vehicle vehicle = store.getVehicles().values().stream()
                .filter(v -> v.getWasteType() == wasteType)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Bu atik turu icin arac bulunamadi"));

        RoutePlan previousActive = getActiveRouteForVehicle(vehicle.getId());
        if (previousActive != null) {
            previousActive.setStatus(RouteStatus.CANCELLED);
        }

        List<LatestState> candidates = selectCandidates(wasteType);
        List<LatestState> ordered = nearestNeighborOrder(vehicle, candidates);

        RoutePlan routePlan = new RoutePlan(
                "route_" + UUID.randomUUID().toString().substring(0, 8),
                vehicle.getId(),
                wasteType,
                RouteStatus.ACTIVE,
                nextVersion(vehicle.getId()),
                OffsetDateTime.now(),
                generationMode
        );

        store.getRoutePlans().put(routePlan.getId(), routePlan);

        int sequence = 1;
        for (LatestState state : ordered) {
            RouteStop stop = new RouteStop(
                    "stop_" + UUID.randomUUID().toString().substring(0, 8),
                    routePlan.getId(),
                    state.getContainerId(),
                    sequence++,
                    StopStatus.PENDING
            );

            store.getRouteStops().put(stop.getId(), stop);
            routePlan.getStopIds().add(stop.getId());
        }

        return routePlan;
    }

    public RoutePlan getActiveRouteForVehicle(String vehicleId) {
        return store.getRoutePlans().values().stream()
                .filter(r -> r.getVehicleId().equals(vehicleId))
                .filter(r -> r.getStatus() == RouteStatus.ACTIVE)
                .max(Comparator.comparingInt(RoutePlan::getVersionNo))
                .orElse(null);
    }

    private int nextVersion(String vehicleId) {
        return store.getRoutePlans().values().stream()
                .filter(r -> r.getVehicleId().equals(vehicleId))
                .map(RoutePlan::getVersionNo)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    private List<LatestState> selectCandidates(WasteType wasteType) {
        return store.getLatestStates().values().stream()
                .filter(s -> s.getWasteType() == wasteType)
                .filter(s -> s.getFillLevelPercent() >= AppConstants.THRESHOLD_PERCENT)
                .collect(Collectors.toList());
    }

    private List<LatestState> nearestNeighborOrder(Vehicle vehicle, List<LatestState> states) {
        List<LatestState> remaining = new ArrayList<>(states);
        List<LatestState> ordered = new ArrayList<>();

        double currentLat = vehicle.getGarageLat();
        double currentLng = vehicle.getGarageLng();

        while (!remaining.isEmpty()) {
            final double refLat = currentLat;
            final double refLng = currentLng;

            LatestState nearest = remaining.stream()
                    .min(Comparator.comparingDouble(s -> distance(refLat, refLng, s.getLat(), s.getLng())))
                    .orElseThrow();

            ordered.add(nearest);
            remaining.remove(nearest);

            currentLat = nearest.getLat();
            currentLng = nearest.getLng();
        }

        return ordered;
    }

    private double distance(double aLat, double aLng, double bLat, double bLng) {
        return Math.sqrt(Math.pow(aLat - bLat, 2) + Math.pow(aLng - bLng, 2));
    }
}