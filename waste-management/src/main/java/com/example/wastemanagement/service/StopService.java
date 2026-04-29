package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.stop.StopStatusUpdateRequest;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.entity.SkippedAlert;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.enums.RouteStatus;
import com.example.wastemanagement.enums.StopStatus;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.CollectionRepository;
import com.example.wastemanagement.repository.RoutePlanRepository;
import com.example.wastemanagement.repository.RouteStopRepository;
import com.example.wastemanagement.repository.TelemetryRepository;
import com.example.wastemanagement.repository.SkippedAlertRepository;
import com.example.wastemanagement.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Comparator;

@Service
public class StopService {

    private final RouteStopRepository routeStopRepository;
    private final RoutePlanRepository routePlanRepository;
    private final CollectionRepository collectionRepository;
    private final TelemetryRepository telemetryRepository;
    
    // 🚨 ATLANAN KONTEYNER BİLDİRİMLERİ İÇİN YENİ EKLENENLER 🚨
    private final SkippedAlertRepository skippedAlertRepository;
    private final VehicleRepository vehicleRepository;

    public StopService(RouteStopRepository routeStopRepository,
                       RoutePlanRepository routePlanRepository,
                       CollectionRepository collectionRepository,
                       TelemetryRepository telemetryRepository,
                       SkippedAlertRepository skippedAlertRepository,
                       VehicleRepository vehicleRepository) { 
        this.routeStopRepository = routeStopRepository;
        this.routePlanRepository = routePlanRepository;
        this.collectionRepository = collectionRepository;
        this.telemetryRepository = telemetryRepository;
        this.skippedAlertRepository = skippedAlertRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public RouteStop updateStopStatus(Long stopId, StopStatusUpdateRequest request, Driver currentDriver) {
        RouteStop stop = routeStopRepository.findById(stopId)
                .orElseThrow(() -> new NotFoundException("Durak bulunamadi"));

        RoutePlan routePlan = validateStopAccess(stop, currentDriver);

        if (routePlan.getStatus() != RouteStatus.ACTIVE) {
            throw new IllegalStateException("Aktif olmayan rotada guncelleme yapilamaz");
        }

        if (request.getStatus() == StopStatus.PENDING) {
            throw new IllegalArgumentException("Durak pending durumuna geri alinamaz");
        }

        if (request.getStatus() == StopStatus.ARRIVED) {
            if (stop.getStatus() != StopStatus.PENDING) {
                throw new IllegalStateException("Sadece pending durak arrived olabilir");
            }
            stop.setStatus(StopStatus.ARRIVED);
        }

        if (request.getStatus() == StopStatus.DONE) {
            boolean collectionExists = collectionRepository.existsByRouteStopId(stop.getId());

            if (!collectionExists) {
                throw new IllegalStateException("Done icin once collection kaydi olusturulmalidir");
            }

            stop.setStatus(StopStatus.DONE);

            // 🚨 SIFIRLAMA (ALTIN MADENİ) MANTIĞI 🚨
            TelemetryRecord lastRecord = telemetryRepository.findAll().stream()
                    .filter(t -> t.getContainerId().equals(stop.getContainerId()))
                    .max(Comparator.comparing(TelemetryRecord::getSourceTimestamp))
                    .orElse(null);

            double lat = lastRecord != null ? lastRecord.getLat() : 0.0;
            double lng = lastRecord != null ? lastRecord.getLng() : 0.0;

            // Sensör %0.0 ölçmüş gibi YENİ bir kayıt atıyoruz
            TelemetryRecord resetRecord = new TelemetryRecord(
                    null, // id
                    stop.getContainerId(), // containerId
                    BigDecimal.ZERO, // İÇİ TAMAMEN BOŞALTILDI (%0.0)
                    lat, // lat
                    lng, // lng
                    OffsetDateTime.now(), // sourceTimestamp
                    OffsetDateTime.now()  // ingestedAt
            );

            telemetryRepository.save(resetRecord);
            System.out.println("♻️ Konteyner #" + stop.getContainerId() + " boşaltıldı ve %0.0 olarak güncellendi.");
        }

        // 🚨 ATLANAN KONTEYNER VE BİLDİRİM MANTIĞI 🚨
        if (request.getStatus() == StopStatus.SKIPPED) {
            stop.setStatus(StopStatus.SKIPPED);

            // Şoförün atama yapıldığı aracı buluyoruz ki plakasını loglayabilelim
            Vehicle vehicle = vehicleRepository.findById(currentDriver.getAssignedVehicleId())
                    .orElseThrow(() -> new IllegalStateException("Araç bulunamadı"));

            // Yeni bildirimi oluşturup veritabanına kaydediyoruz
            SkippedAlert alert = new SkippedAlert(
                    stop.getContainerId(),
                    currentDriver.getId(),
                    vehicle.getPlate(),
                    request.getReason() != null && !request.getReason().trim().isEmpty() 
                            ? request.getReason() 
                            : "Sebep belirtilmedi",
                    OffsetDateTime.now()
            );

            skippedAlertRepository.save(alert);
            System.out.println("🚨 Atlanan Konteyner Kaydedildi! Sebep: " + alert.getReason());
        }

        return routeStopRepository.save(stop);
    }

    public RoutePlan validateStopAccess(RouteStop stop, Driver currentDriver) {
        RoutePlan routePlan = routePlanRepository.findById(stop.getRoutePlanId())
                .orElseThrow(() -> new NotFoundException("Rota bulunamadi"));

        if (!routePlan.getVehicleId().equals(currentDriver.getAssignedVehicleId())) {
            throw new IllegalArgumentException("Bu duraga erisim yetkiniz yok");
        }

        return routePlan;
    }
}