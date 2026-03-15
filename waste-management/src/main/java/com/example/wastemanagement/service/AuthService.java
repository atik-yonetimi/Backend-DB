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
                .filter(d -> {
                    Vehicle vehicle = store.getVehicles().get(d.getVehicleId());
                    return vehicle != null && vehicle.getPlate().equalsIgnoreCase(plate);
                })
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Plakaya ait sürücü bulunamadı"));

        Vehicle vehicle = store.getVehicles().get(driver.getVehicleId());
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
        Vehicle vehicle = store.getVehicles().get(driver.getVehicleId());

        if (vehicle == null) {
            throw new NotFoundException("Araç bulunamadı");
        }

        MeResponse response = new MeResponse();
        response.setDriverId(driver.getId());
        response.setFullName(driver.getFullName());
        response.setVehicleId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setWasteType(vehicle.getWasteType().name());

        return response;
    }
}