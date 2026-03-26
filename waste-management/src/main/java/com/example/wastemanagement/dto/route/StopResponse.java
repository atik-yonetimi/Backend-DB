package com.example.wastemanagement.dto.route;

public class StopResponse {

    private Long id;
    private Integer sequenceNo;
    private String status;
    private ContainerSummaryResponse container;

    public StopResponse() {
    }

    public Long getId() {
        return id;
    }

    public Integer getSequenceNo() {
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

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContainer(ContainerSummaryResponse container) {
        this.container = container;
    }
}