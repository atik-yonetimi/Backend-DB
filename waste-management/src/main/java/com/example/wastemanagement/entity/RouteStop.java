package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.StopStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.OffsetDateTime;

@Entity
@Table(name = "route_stops")
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_plan_id")
    private Long routePlanId;

    @Column(name = "container_id")
    private Long containerId;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Enumerated(EnumType.STRING)
    private StopStatus status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public RouteStop() {
    }

    public RouteStop(Long id, Long routePlanId, Long containerId,
                     Integer sequenceNo, StopStatus status, OffsetDateTime createdAt) {
        this.id = id;
        this.routePlanId = routePlanId;
        this.containerId = containerId;
        this.sequenceNo = sequenceNo;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getRoutePlanId() {
        return routePlanId;
    }

    public Long getContainerId() {
        return containerId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public StopStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoutePlanId(Long routePlanId) {
        this.routePlanId = routePlanId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(StopStatus status) {
        this.status = status;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}