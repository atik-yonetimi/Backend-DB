package com.example.wastemanagement.entity;

import com.example.wastemanagement.enums.StopStatus;

import java.time.OffsetDateTime;

public class RouteStop {
    private String id;
    private String routePlanId;
    private String containerId;
    private int sequenceNo;
    private StopStatus status;
    private OffsetDateTime arrivalAt;
    private OffsetDateTime completedAt;
    private OffsetDateTime skippedAt;
    private String skipReason;

    public RouteStop() {
    }

    public RouteStop(String id, String routePlanId, String containerId,
                     int sequenceNo, StopStatus status) {
        this.id = id;
        this.routePlanId = routePlanId;
        this.containerId = containerId;
        this.sequenceNo = sequenceNo;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getRoutePlanId() {
        return routePlanId;
    }

    public String getContainerId() {
        return containerId;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public StopStatus getStatus() {
        return status;
    }

    public OffsetDateTime getArrivalAt() {
        return arrivalAt;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public OffsetDateTime getSkippedAt() {
        return skippedAt;
    }

    public String getSkipReason() {
        return skipReason;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoutePlanId(String routePlanId) {
        this.routePlanId = routePlanId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(StopStatus status) {
        this.status = status;
    }

    public void setArrivalAt(OffsetDateTime arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public void setCompletedAt(OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setSkippedAt(OffsetDateTime skippedAt) {
        this.skippedAt = skippedAt;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }
}