package com.example.wastemanagement.service;

import com.example.wastemanagement.repository.InMemoryStore;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

        Long routePlanId = (long) (store.getRoutePlans().size() + 1);

        RoutePlan routePlan = new RoutePlan(
                routePlanId,
                vehicle.getId(),
                wasteType,
                RouteStatus.ACTIVE,
                OffsetDateTime.now()
        );

        store.getRoutePlans().put(routePlan.getId(), routePlan);

        int sequence = 1;
        for (LatestState state : ordered) {
            Long stopId = (long) (store.getRouteStops().size() + 1);

            RouteStop stop = new RouteStop(
                    stopId,
                    routePlan.getId(),
                    state.getContainerId(),
                    sequence++,
                    StopStatus.PENDING,
                    OffsetDateTime.now()
            );

            store.getRouteStops().put(stop.getId(), stop);
        }

        return routePlan;
    }

    public RoutePlan getActiveRouteForVehicle(Long vehicleId) {
        return store.getRoutePlans().values().stream()
                .filter(r -> r.getVehicleId().equals(vehicleId))
                .filter(r -> r.getStatus() == RouteStatus.ACTIVE)
                .max(Comparator.comparing(RoutePlan::getCreatedAt))
                .orElse(null);
    }

    private List<LatestState> selectCandidates(WasteType wasteType) {
        return store.getLatestStates().values().stream()
                .filter(s -> s.getWasteType() == wasteType)
                .filter(s -> s.getFillPercent().compareTo(BigDecimal.valueOf(AppConstants.THRESHOLD_PERCENT)) >= 0)
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

    private double distance(Double aLat, Double aLng, Double bLat, Double bLng) {
        return Math.sqrt(Math.pow(aLat - bLat, 2) + Math.pow(aLng - bLng, 2));
    }
}