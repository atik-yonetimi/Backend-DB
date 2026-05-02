package com.example.wastemanagement.controller;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 🚨 1. DÜZELTME: Araçları Listelerken Sürücü ID'lerini de bulup ekliyoruz 🚨
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Driver> drivers = driverRepository.findAll();

        List<Map<String, Object>> responseList = vehicles.stream().map(vehicle -> {
            // Bu araca atanmış sürücüyü bul
            Driver matchingDriver = drivers.stream()
                    .filter(d -> d.getAssignedVehicleId().equals(vehicle.getId()))
                    .findFirst()
                    .orElse(null);

            // Araç bilgileriyle birlikte Sürücü ID'sini de harmanlayıp Flutter'a yolla
            Map<String, Object> map = new HashMap<>();
            map.put("id", vehicle.getId());
            map.put("plate", vehicle.getPlate());
            map.put("wasteType", vehicle.getWasteType().name());
            map.put("garageLat", vehicle.getGarageLat());
            map.put("garageLng", vehicle.getGarageLng());
            map.put("driverId", matchingDriver != null ? matchingDriver.getId() : 0);

            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
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

    // 🚨 2. DÜZELTME: Eksik olan Silme (DELETE) Kapısını Ekliyoruz 🚨
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        try {
            // Önce bu araca bağlı olan sürücüyü bulup silmemiz lazım (Foreign Key constraint yüzünden)
            Driver driver = driverRepository.findAll().stream()
                    .filter(d -> d.getAssignedVehicleId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (driver != null) {
                driverRepository.delete(driver);
            }

            // Sürücü silindikten sonra aracı güvenle silebiliriz
            vehicleRepository.deleteById(id);

            return ResponseEntity.ok("Araç başarıyla silindi!");
        } catch (Exception e) {
            // Eğer geçmişte çöp toplamış veya rotaya çıkmış bir araçsa veritabanı silmeye izin vermeyebilir
            return ResponseEntity.badRequest().body("Bu araç silinemez! Çünkü üzerinde aktif bir rota veya geçmiş çöp toplama kaydı bulunuyor.");
        }
    }
}