package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.route.GenerateRouteRequest;
import com.example.wastemanagement.dto.route.RoutePlanResponse;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.security.TokenService;
import com.example.wastemanagement.service.RouteService;
import com.example.wastemanagement.util.RouteMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;
    private final TokenService tokenService;

    public RouteController(RouteService routeService, RouteMapper routeMapper, TokenService tokenService) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
        this.tokenService = tokenService;
    }

    @PostMapping("/generate")
    public RoutePlanResponse generate(@Valid @RequestBody GenerateRouteRequest request) {
        RoutePlan routePlan = routeService.generateRoute(request.getWasteType(), request.getGenerationMode());
        return routeMapper.toRoutePlanResponse(routePlan);
    }

    @GetMapping("/active")
    public RoutePlanResponse active(@RequestHeader("Authorization") String authorizationHeader) {
        Driver driver = tokenService.getDriverFromAuthorizationHeader(authorizationHeader);
        RoutePlan routePlan = routeService.getActiveRouteForVehicle(driver.getAssignedVehicleId());

        if (routePlan == null) {
            throw new IllegalArgumentException("Aktif rota bulunamadi");
        }

        return routeMapper.toRoutePlanResponse(routePlan);
    }
}