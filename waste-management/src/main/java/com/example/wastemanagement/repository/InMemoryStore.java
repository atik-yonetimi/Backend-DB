package com.example.wastemanagement.repository;

import com.example.wastemanagement.entity.CollectionRecord;
import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.LatestState;
import com.example.wastemanagement.entity.RoutePlan;
import com.example.wastemanagement.entity.RouteStop;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStore {

    private final Map<String, Driver> drivers = new ConcurrentHashMap<>();
    private final Map<String, Vehicle> vehicles = new ConcurrentHashMap<>();
    private final Map<String, Container> containers = new ConcurrentHashMap<>();
    private final Map<String, TelemetryRecord> telemetryRecords = new ConcurrentHashMap<>();
    private final Map<String, LatestState> latestStates = new ConcurrentHashMap<>();
    private final Map<String, RoutePlan> routePlans = new ConcurrentHashMap<>();
    private final Map<String, RouteStop> routeStops = new ConcurrentHashMap<>();
    private final Map<String, CollectionRecord> collections = new ConcurrentHashMap<>();
    private final Map<String, String> tokens = new ConcurrentHashMap<>();

    public Map<String, Driver> getDrivers() {
        return drivers;
    }

    public Map<String, Vehicle> getVehicles() {
        return vehicles;
    }

    public Map<String, Container> getContainers() {
        return containers;
    }

    public Map<String, TelemetryRecord> getTelemetryRecords() {
        return telemetryRecords;
    }

    public Map<String, LatestState> getLatestStates() {
        return latestStates;
    }

    public Map<String, RoutePlan> getRoutePlans() {
        return routePlans;
    }

    public Map<String, RouteStop> getRouteStops() {
        return routeStops;
    }

    public Map<String, CollectionRecord> getCollections() {
        return collections;
    }

    public Map<String, String> getTokens() {
        return tokens;
    }
}