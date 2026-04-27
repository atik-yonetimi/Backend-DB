package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.auth.AdminLoginRequest;
import com.example.wastemanagement.dto.auth.AdminLoginResponse;
import com.example.wastemanagement.dto.auth.LoginResponse;
import com.example.wastemanagement.dto.auth.MeResponse;
import com.example.wastemanagement.entity.Admin;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.AdminRepository;
import com.example.wastemanagement.repository.DriverRepository;
import com.example.wastemanagement.repository.VehicleRepository;
import com.example.wastemanagement.security.TokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    public AuthService(DriverRepository driverRepository,
                       VehicleRepository vehicleRepository,
                       AdminRepository adminRepository,
                       TokenService tokenService) {
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.adminRepository = adminRepository;
        this.tokenService = tokenService;
    }

    public LoginResponse loginByPlate(String plate) {
        Driver driver = driverRepository.findByPlateLoginIgnoreCase(plate)
                .orElseThrow(() -> new NotFoundException("Plakaya ait surucu bulunamadi"));

        Vehicle vehicle = vehicleRepository.findById(driver.getAssignedVehicleId())
                .orElseThrow(() -> new NotFoundException("Surucuye atanmis arac bulunamadi"));

        String token = tokenService.generateToken(driver.getId());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setDriverId(driver.getId());
        response.setVehicleId(vehicle.getId());
        response.setPlate(vehicle.getPlate());

        return response;
    }

    public AdminLoginResponse adminLogin(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUsernameAndPassword(
                        request.getUsername(),
                        request.getPassword()
                )
                .orElseThrow(() -> new NotFoundException("Admin kullanici adi veya sifre hatali"));

        // 🚨 EKSİK OLAN TOKEN ÜRETİMİ BURAYA EKLENDİ 🚨
        String token = tokenService.generateToken(admin.getId());

        AdminLoginResponse response = new AdminLoginResponse();
        response.setAdminId(admin.getId());
        response.setUsername(admin.getUsername());
        response.setRole("ADMIN");
        
        // 🚨 ÜRETİLEN TOKEN KUTUYA (RESPONSE) KONULDU 🚨
        response.setAccessToken(token); 

        return response;
    }

    public MeResponse me(Driver driver) {
        Vehicle vehicle = vehicleRepository.findById(driver.getAssignedVehicleId())
                .orElseThrow(() -> new NotFoundException("Arac bulunamadi"));

        MeResponse response = new MeResponse();
        response.setDriverId(driver.getId());
        response.setVehicleId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setWasteType(vehicle.getWasteType().name());

        return response;
    }
}