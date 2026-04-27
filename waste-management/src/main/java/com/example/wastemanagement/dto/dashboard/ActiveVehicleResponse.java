package com.example.wastemanagement.dto.dashboard;

public class ActiveVehicleResponse {

    private Long vehicleId;
    private String plate;
    private String wasteType;
    private Long routePlanId;
    private String routeStatus;
    private int totalStops;
    private int pendingStops;
    private int doneStops;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public Long getRoutePlanId() {
        return routePlanId;
    }

    public void setRoutePlanId(Long routePlanId) {
        this.routePlanId = routePlanId;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }

    public int getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(int totalStops) {
        this.totalStops = totalStops;
    }

    public int getPendingStops() {
        return pendingStops;
    }

    public void setPendingStops(int pendingStops) {
        this.pendingStops = pendingStops;
    }

    public int getDoneStops() {
        return doneStops;
    }

    public void setDoneStops(int doneStops) {
        this.doneStops = doneStops;
    }
}