package com.example.wastemanagement.dto.route;

import java.math.BigDecimal;

public class ContainerSummaryResponse {

    private Long id;
    private String wasteType;
    private Double lat;
    private Double lng;
    private BigDecimal fillPercent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public BigDecimal getFillPercent() {
        return fillPercent;
    }

    public void setFillPercent(BigDecimal fillPercent) {
        this.fillPercent = fillPercent;
    }
}