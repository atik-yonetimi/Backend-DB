package com.example.wastemanagement.dto.route;

import java.time.OffsetDateTime;

public class StopResponse {

    private Long id;
    private int sequenceNo;
    private String status;
    private ContainerSummaryResponse container;

    public StopResponse() {
    }

    public Long getId() {
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


    public void setId(Long id) {
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

}