package com.example.wastemanagement.util;

import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.LatestState;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.enums.WasteType;
import com.example.wastemanagement.repository.InMemoryStore;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
public class SeedDataLoader {

    private final InMemoryStore store;

    public SeedDataLoader(InMemoryStore store) {
        this.store = store;
    }

    @PostConstruct
    public void load() {
        if (!store.getVehicles().isEmpty()) {
            return;
        }

        OffsetDateTime now = OffsetDateTime.now();

        store.getVehicles().put(1L,
                new Vehicle(1L, "34GLS001", WasteType.CAM, 41.0082, 28.9784, now));
        store.getVehicles().put(2L,
                new Vehicle(2L, "34PLS001", WasteType.PLASTIK, 41.0082, 28.9784, now));
        store.getVehicles().put(3L,
                new Vehicle(3L, "34PPR001", WasteType.KAGIT, 41.0082, 28.9784, now));
        store.getVehicles().put(4L,
                new Vehicle(4L, "34SHD001", WasteType.IKINCI_EL_ESYA, 41.0082, 28.9784, now));
        store.getVehicles().put(5L,
                new Vehicle(5L, "34MTL001", WasteType.METAL, 41.0082, 28.9784, now));

        store.getDrivers().put(1L,
                new Driver(1L, "34GLS001", 1L, now));
        store.getDrivers().put(2L,
                new Driver(2L, "34PLS001", 2L, now));
        store.getDrivers().put(3L,
                new Driver(3L, "34PPR001", 3L, now));
        store.getDrivers().put(4L,
                new Driver(4L, "34SHD001", 4L, now));
        store.getDrivers().put(5L,
                new Driver(5L, "34MTL001", 5L, now));

        store.getContainers().put(1L,
                new Container(1L, WasteType.CAM, 41.015, 28.984, "ACTIVE", now));
        store.getContainers().put(2L,
                new Container(2L, WasteType.CAM, 41.021, 28.991, "ACTIVE", now));
        store.getContainers().put(3L,
                new Container(3L, WasteType.CAM, 41.027, 28.998, "ACTIVE", now));
        store.getContainers().put(4L,
                new Container(4L, WasteType.PLASTIK, 41.011, 28.986, "ACTIVE", now));
        store.getContainers().put(5L,
                new Container(5L, WasteType.PLASTIK, 41.016, 28.995, "ACTIVE", now));
        store.getContainers().put(6L,
                new Container(6L, WasteType.KAGIT, 41.013, 28.981, "ACTIVE", now));
        store.getContainers().put(7L,
                new Container(7L, WasteType.METAL, 41.017, 28.992, "ACTIVE", now));

        store.getLatestStates().put(1L,
                new LatestState(1L, WasteType.CAM, BigDecimal.valueOf(78), 41.015, 28.984, now, now));
        store.getLatestStates().put(2L,
                new LatestState(2L, WasteType.CAM, BigDecimal.valueOf(64), 41.021, 28.991, now, now));
        store.getLatestStates().put(3L,
                new LatestState(3L, WasteType.CAM, BigDecimal.valueOf(42), 41.027, 28.998, now, now));
        store.getLatestStates().put(4L,
                new LatestState(4L, WasteType.PLASTIK, BigDecimal.valueOf(82), 41.011, 28.986, now, now));
        store.getLatestStates().put(5L,
                new LatestState(5L, WasteType.PLASTIK, BigDecimal.valueOf(61), 41.016, 28.995, now, now));
        store.getLatestStates().put(6L,
                new LatestState(6L, WasteType.KAGIT, BigDecimal.valueOf(58), 41.013, 28.981, now, now));
        store.getLatestStates().put(7L,
                new LatestState(7L, WasteType.METAL, BigDecimal.valueOf(88), 41.017, 28.992, now, now));
    }
}