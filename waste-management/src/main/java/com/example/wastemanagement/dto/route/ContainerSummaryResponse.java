package com.example.wastemanagement.dto.route;

public class ContainerSummaryResponse {

    private Long id;
    private String wasteType;
    private Double lat;
    private Double lng;

    public ContainerSummaryResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getWasteType() {
        return wasteType;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}