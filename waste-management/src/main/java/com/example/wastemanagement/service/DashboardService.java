package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.dashboard.ActiveVehicleResponse;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.RoutePlanRepository;
import com.example.wastemanagement.repository.RouteStopRepository;
import com.example.wastemanagement.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final RoutePlanRepository routePlanRepository;
    private final RouteStopRepository routeStopRepository;
    private final VehicleRepository vehicleRepository;

    public DashboardService(RoutePlanRepository routePlanRepository,
                            RouteStopRepository routeStopRepository,
                            VehicleRepository vehicleRepository) {
        this.routePlanRepository = routePlanRepository;
        this.routeStopRepository = routeStopRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<ActiveVehicleResponse> getActiveVehicles() {
        return routePlanRepository.findByStatus(RouteStatus.ACTIVE)
                .stream()
                .map(this::toActiveVehicleResponse)
                .toList();
    }

    private ActiveVehicleResponse toActiveVehicleResponse(RoutePlan routePlan) {
        Vehicle vehicle = vehicleRepository.findById(routePlan.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Arac bulunamadi"));

        ActiveVehicleResponse response = new ActiveVehicleResponse();
        response.setVehicleId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setWasteType(vehicle.getWasteType().name());
        response.setRoutePlanId(routePlan.getId());
        response.setRouteStatus(routePlan.getStatus().name());

        response.setTotalStops(routeStopRepository.countByRoutePlanId(routePlan.getId()));
        response.setPendingStops(routeStopRepository.countByRoutePlanIdAndStatus(routePlan.getId(), StopStatus.PENDING));
        response.setDoneStops(routeStopRepository.countByRoutePlanIdAndStatus(routePlan.getId(), StopStatus.DONE));

        return response;
    }
}