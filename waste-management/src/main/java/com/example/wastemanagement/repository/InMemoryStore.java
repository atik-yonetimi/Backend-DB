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

    private final Map<Long, Driver> drivers = new ConcurrentHashMap<>();
    private final Map<Long, Vehicle> vehicles = new ConcurrentHashMap<>();
    private final Map<Long, Container> containers = new ConcurrentHashMap<>();
    private final Map<Long, TelemetryRecord> telemetryRecords = new ConcurrentHashMap<>();
    private final Map<Long, LatestState> latestStates = new ConcurrentHashMap<>();
    private final Map<Long, RoutePlan> routePlans = new ConcurrentHashMap<>();
    private final Map<Long, RouteStop> routeStops = new ConcurrentHashMap<>();
    private final Map<Long, CollectionRecord> collections = new ConcurrentHashMap<>();
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();

    public Map<Long, Driver> getDrivers() {
        return drivers;
    }

    public Map<Long, Vehicle> getVehicles() {
        return vehicles;
    }

    public Map<Long, Container> getContainers() {
        return containers;
    }

    public Map<Long, TelemetryRecord> getTelemetryRecords() {
        return telemetryRecords;
    }

    public Map<Long, LatestState> getLatestStates() {
        return latestStates;
    }

    public Map<Long, RoutePlan> getRoutePlans() {
        return routePlans;
    }

    public Map<Long, RouteStop> getRouteStops() {
        return routeStops;
    }

    public Map<Long, CollectionRecord> getCollections() {
        return collections;
    }

    public Map<String, Long> getTokens() {
        return tokens;
    }
}