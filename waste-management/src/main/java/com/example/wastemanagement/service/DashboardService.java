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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<RoutePlan> activeRoutes = routePlanRepository.findByStatus(RouteStatus.ACTIVE);

        Map<Long, RoutePlan> latestActiveRouteByVehicle = activeRoutes.stream()
                .collect(Collectors.toMap(
                        RoutePlan::getVehicleId,
                        routePlan -> routePlan,
                        (r1, r2) -> r1.getCreatedAt().isAfter(r2.getCreatedAt()) ? r1 : r2
                ));

        return latestActiveRouteByVehicle.values()
                .stream()
                .sorted(Comparator.comparing(RoutePlan::getVehicleId))
                .map(this::toActiveVehicleResponse)
                // 🚨 İŞTE SİHİRLİ DOKUNUŞ BURADA: Toplam durağı 0 olan hayalet rotaları listeden gizle! 🚨
                .filter(response -> response.getTotalStops() > 0)
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

        int totalStops = routeStopRepository.countByRoutePlanId(routePlan.getId());
        int pendingStops = routeStopRepository.countByRoutePlanIdAndStatus(routePlan.getId(), StopStatus.PENDING);
        int doneStops = routeStopRepository.countByRoutePlanIdAndStatus(routePlan.getId(), StopStatus.DONE);

        response.setTotalStops(totalStops);
        response.setPendingStops(pendingStops);
        response.setDoneStops(doneStops);

        return response;
    }
}