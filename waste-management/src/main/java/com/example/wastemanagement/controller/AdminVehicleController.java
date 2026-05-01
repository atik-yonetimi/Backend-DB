package com.example.wastemanagement.controller;

import java.time.OffsetDateTime; // 🚨 YENİ KLASÖR YOLU
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wastemanagement.dto.vehicle.VehicleCreateRequest;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.repository.DriverRepository;
import com.example.wastemanagement.repository.VehicleRepository;

@RestController
@RequestMapping("/api/admin/vehicles")
@CrossOrigin("*") // Web panelinden (Flutter Web) sorunsuz istek atabilmek için
public class AdminVehicleController {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public AdminVehicleController(VehicleRepository vehicleRepository, DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }
    // 🚨 YENİ EKLENEN: Tüm Araçları Getiren Endpoint 🚨

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        // Veritabanındaki tüm araçları bul ve Flutter'a liste olarak gönder
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<String> addVehicleAndDriver(@RequestBody VehicleCreateRequest request) {

        // 1. Önce Aracı (Vehicle) oluştur ve kaydet
        Vehicle newVehicle = new Vehicle();
        newVehicle.setPlate(request.getPlate());
        newVehicle.setWasteType(request.getWasteType());
        newVehicle.setLoginPassword(request.getLoginPassword());
        newVehicle.setGarageLat(request.getGarageLat());
        newVehicle.setGarageLng(request.getGarageLng());
        newVehicle.setCreatedAt(OffsetDateTime.now());

        Vehicle savedVehicle = vehicleRepository.save(newVehicle);

        // 2. Araca bağlı Sürücüyü (Driver) oluştur ve kaydet
        Driver newDriver = new Driver();
        newDriver.setPlateLogin(savedVehicle.getPlate()); // Giriş plakası araçla aynı yapıldı
        newDriver.setAssignedVehicleId(savedVehicle.getId());
        newDriver.setCreatedAt(OffsetDateTime.now());

        driverRepository.save(newDriver);

        return ResponseEntity.ok("Araç ve Sürücü başarıyla sisteme eklendi!");
    }
}