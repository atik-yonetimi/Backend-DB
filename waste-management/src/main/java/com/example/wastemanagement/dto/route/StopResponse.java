package com.example.wastemanagement.dto.route;

import java.time.OffsetDateTime;

public class StopResponse {

    private String id;
    private int sequenceNo;
    private String status;
    private ContainerSummaryResponse container;
    private OffsetDateTime arrivalAt;
    private OffsetDateTime completedAt;
    private OffsetDateTime skippedAt;
    private String skipReason;

    public StopResponse() {
    }

    public String getId() {
        return id;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public String getStatus() {
        return status;
    }

    public ContainerSummaryResponse getContainer() {
        return container;
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

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContainer(ContainerSummaryResponse container) {
        this.container = container;
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