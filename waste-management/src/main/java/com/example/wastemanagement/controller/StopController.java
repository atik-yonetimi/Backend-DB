package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.route.StopResponse;
import com.example.wastemanagement.dto.stop.StopStatusUpdateRequest;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.security.TokenService;
import com.example.wastemanagement.service.StopService;
import com.example.wastemanagement.util.RouteMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stops")
public class StopController {

    private final StopService stopService;
    private final RouteMapper routeMapper;
    private final TokenService tokenService;

    public StopController(StopService stopService, RouteMapper routeMapper, TokenService tokenService) {
        this.stopService = stopService;
        this.routeMapper = routeMapper;
        this.tokenService = tokenService;
    }

    @PatchMapping("/{stopId}/status")
    public StopResponse updateStatus(@PathVariable String stopId,
                                     @Valid @RequestBody StopStatusUpdateRequest request,
                                     @RequestHeader("Authorization") String authorizationHeader) {
        Driver driver = tokenService.getDriverFromAuthorizationHeader(authorizationHeader);
        RouteStop updatedStop = stopService.updateStopStatus(stopId, request, driver);
        return routeMapper.toStopResponse(updatedStop);
    }
}