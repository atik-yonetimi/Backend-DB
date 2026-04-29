package com.example.wastemanagement.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "skipped_alerts")
public class SkippedAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "container_id")
    private Long containerId;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "vehicle_plate")
    private String vehiclePlate;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // Boş Constructor (JPA için zorunlu)
    public SkippedAlert() {}

    public SkippedAlert(Long containerId, Long driverId, String vehiclePlate, String reason, OffsetDateTime createdAt) {
        this.containerId = containerId;
        this.driverId = driverId;
        this.vehiclePlate = vehiclePlate;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    // --- Getter ve Setter Metotları ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContainerId() { return containerId; }
    public void setContainerId(Long containerId) { this.containerId = containerId; }

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}