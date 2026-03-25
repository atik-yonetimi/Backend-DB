package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.StopStatus;

import java.time.OffsetDateTime;

public class RouteStop {
    private Long id;
    private Long routePlanId;
    private Long containerId;
    private int sequenceNo;
    private StopStatus status;
    private OffsetDateTime createdAt;

    public RouteStop() {
    }

    public RouteStop(Long id, Long routePlanId, Long containerId,
                     int sequenceNo, StopStatus status,OffsetDateTime createdAt ) {
        this.id = id;
        this.routePlanId = routePlanId;
        this.containerId = containerId;
        this.sequenceNo = sequenceNo;
        this.status = status;
        this.createdAt= createdAt;
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

    public int getSequenceNo() {
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

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(StopStatus status) {
        this.status = status;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}