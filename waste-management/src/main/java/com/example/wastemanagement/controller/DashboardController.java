package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.dashboard.ActiveVehicleResponse;
import com.example.wastemanagement.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/active-vehicles")
    public List<ActiveVehicleResponse> getActiveVehicles() {
        return dashboardService.getActiveVehicles();
    }
}