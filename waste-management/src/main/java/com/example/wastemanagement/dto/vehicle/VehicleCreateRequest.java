package com.example.wastemanagement.dto.vehicle;

import com.example.wastemanagement.enums.WasteType;

public class VehicleCreateRequest {

    private String plate;
    private WasteType wasteType;
    private String loginPassword;
    private Double garageLat;
    private Double garageLng;

    // --- Getter ve Setter Metotları ---
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public void setWasteType(WasteType wasteType) {
        this.wasteType = wasteType;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Double getGarageLat() {
        return garageLat;
    }

    public void setGarageLat(Double garageLat) {
        this.garageLat = garageLat;
    }

    public Double getGarageLng() {
        return garageLng;
    }

    public void setGarageLng(Double garageLng) {
        this.garageLng = garageLng;
    }
}