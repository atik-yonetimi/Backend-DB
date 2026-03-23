package com.example.wastemanagement.util;

import com.example.wastemanagement.dto.route.ContainerSummaryResponse;
import com.example.wastemanagement.dto.route.RoutePlanResponse;
import com.example.wastemanagement.dto.route.StopResponse;
import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.repository.InMemoryStore;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteMapper {

    private final InMemoryStore store;

    public RouteMapper(InMemoryStore store) {
        this.store = store;
    }

    public RoutePlanResponse toRoutePlanResponse(RoutePlan routePlan) {
        RoutePlanResponse response = new RoutePlanResponse();
        response.setId(routePlan.getId());
        response.setVehicleId(routePlan.getVehicleId());
        response.setWasteType(routePlan.getWasteType().name());
        response.setStatus(routePlan.getStatus().name());
        response.setVersionNo(routePlan.getVersionNo());
        response.setGeneratedAt(routePlan.getGeneratedAt());

        List<StopResponse> stops = routePlan.getStopIds().stream()
                .map(id -> store.getRouteStops().get(id))
                .sorted(Comparator.comparingInt(RouteStop::getSequenceNo))
                .map(this::toStopResponse)
                .collect(Collectors.toList());

        response.setStops(stops);
        return response;
    }

    public StopResponse toStopResponse(RouteStop stop) {
        Container container = store.getContainers().get(stop.getContainerId());

        ContainerSummaryResponse containerResponse = new ContainerSummaryResponse();
        containerResponse.setId(container.getId());
        containerResponse.setContainerCode(container.getContainerCode());
        containerResponse.setWasteType(container.getWasteType().name());
        containerResponse.setLat(container.getLat());
        containerResponse.setLng(container.getLng());

        StopResponse response = new StopResponse();
        response.setId(stop.getId());
        response.setSequenceNo(stop.getSequenceNo());
        response.setStatus(stop.getStatus().name());
        response.setArrivalAt(stop.getArrivalAt());
        response.setCompletedAt(stop.getCompletedAt());
        response.setSkippedAt(stop.getSkippedAt());
        response.setSkipReason(stop.getSkipReason());
        response.setContainer(containerResponse);

        return response;
    }
}