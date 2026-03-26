package com.example.wastemanagement.util;

import com.example.wastemanagement.dto.route.ContainerSummaryResponse;
import com.example.wastemanagement.dto.route.RoutePlanResponse;
import com.example.wastemanagement.dto.route.StopResponse;
import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.ContainerRepository;
import com.example.wastemanagement.repository.RouteStopRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteMapper {

    private final RouteStopRepository routeStopRepository;
    private final ContainerRepository containerRepository;

    public RouteMapper(RouteStopRepository routeStopRepository,
                       ContainerRepository containerRepository) {
        this.routeStopRepository = routeStopRepository;
        this.containerRepository = containerRepository;
    }

    public RoutePlanResponse toRoutePlanResponse(RoutePlan routePlan) {
        RoutePlanResponse response = new RoutePlanResponse();
        response.setId(routePlan.getId());
        response.setVehicleId(routePlan.getVehicleId());
        response.setWasteType(routePlan.getWasteType().name());
        response.setStatus(routePlan.getStatus().name());
        response.setGeneratedAt(routePlan.getCreatedAt());

        List<StopResponse> stops = routeStopRepository.findByRoutePlanIdOrderBySequenceNoAsc(routePlan.getId())
                .stream()
                .map(this::toStopResponse)
                .collect(Collectors.toList());

        response.setStops(stops);
        return response;
    }

    public StopResponse toStopResponse(RouteStop stop) {
        Container container = containerRepository.findById(stop.getContainerId())
                .orElseThrow(() -> new NotFoundException("Container bulunamadi: " + stop.getContainerId()));

        ContainerSummaryResponse containerResponse = new ContainerSummaryResponse();
        containerResponse.setId(container.getId());
        containerResponse.setWasteType(container.getWasteType().name());
        containerResponse.setLat(container.getLat());
        containerResponse.setLng(container.getLng());

        StopResponse response = new StopResponse();
        response.setId(stop.getId());
        response.setSequenceNo(stop.getSequenceNo());
        response.setStatus(stop.getStatus().name());
        response.setContainer(containerResponse);

        return response;
    }
}