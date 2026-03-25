package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.auth.LoginResponse;
import com.example.wastemanagement.dto.auth.MeResponse;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.InMemoryStore;
import com.example.wastemanagement.security.TokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final InMemoryStore store;
    private final TokenService tokenService;

    public AuthService(InMemoryStore store, TokenService tokenService) {
        this.store = store;
        this.tokenService = tokenService;
    }

    public LoginResponse loginByPlate(String plate) {
        Driver driver = store.getDrivers().values().stream()
                .filter(d -> d.getPlateLogin() != null && d.getPlateLogin().equalsIgnoreCase(plate))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Plakaya ait surucu bulunamadi"));

        Vehicle vehicle = store.getVehicles().get(driver.getAssignedVehicleId());
        if (vehicle == null) {
            throw new NotFoundException("Surucuye atanmis arac bulunamadi");
        }

        String token = tokenService.generateToken(driver.getId());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setDriverId(driver.getId());
        response.setVehicleId(vehicle.getId());
        response.setPlate(vehicle.getPlate());

        return response;
    }

    public MeResponse me(Driver driver) {
        Vehicle vehicle = store.getVehicles().get(driver.getAssignedVehicleId());

        if (vehicle == null) {
            throw new NotFoundException("Arac bulunamadi");
        }

        MeResponse response = new MeResponse();
        response.setDriverId(driver.getId());
        response.setVehicleId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setWasteType(vehicle.getWasteType().name());

        return response;
    }
}