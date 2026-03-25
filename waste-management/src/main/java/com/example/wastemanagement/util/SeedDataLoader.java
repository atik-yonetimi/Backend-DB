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
import java.util.Map;

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
        store.getContainers().put(101L,
                new Container(101L, WasteType.CAM, 41.010, 28.980, "ACTIVE", now));
        store.getContainers().put(102L,
                new Container(102L, WasteType.CAM, 41.012, 28.986, "ACTIVE", now));
        store.getContainers().put(103L,
                new Container(103L, WasteType.CAM, 41.016, 28.992, "ACTIVE", now));
        store.getContainers().put(104L,
                new Container(104L, WasteType.PLASTIK, 41.018, 28.975, "ACTIVE", now));
        store.getContainers().put(105L,
                new Container(105L, WasteType.PLASTIK, 41.020, 28.982, "ACTIVE", now));
        store.getContainers().put(106L,
                new Container(106L, WasteType.PLASTIK, 41.022, 28.988, "ACTIVE", now));
        store.getContainers().put(107L,
                new Container(107L, WasteType.KAGIT, 41.014, 28.970, "ACTIVE", now));
        store.getContainers().put(108L,
                new Container(108L, WasteType.KAGIT, 41.017, 28.976, "ACTIVE", now));
        store.getContainers().put(109L,
                new Container(109L, WasteType.KAGIT, 41.019, 28.983, "ACTIVE", now));
        store.getContainers().put(110L,
                new Container(110L, WasteType.IKINCI_EL_ESYA, 41.023, 28.978, "ACTIVE", now));
        store.getContainers().put(111L,
                new Container(111L, WasteType.IKINCI_EL_ESYA, 41.025, 28.984, "ACTIVE", now));
        store.getContainers().put(112L,
                new Container(112L, WasteType.IKINCI_EL_ESYA, 41.027, 28.990, "ACTIVE", now));
        store.getContainers().put(113L,
                new Container(113L, WasteType.METAL, 41.011, 28.972, "ACTIVE", now));
        store.getContainers().put(114L,
                new Container(114L, WasteType.METAL, 41.013, 28.978, "ACTIVE", now));
        store.getContainers().put(115L,
                new Container(115L, WasteType.METAL, 41.015, 28.985, "ACTIVE", now));

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
        store.getLatestStates().put(101L,
                new LatestState(101L, WasteType.CAM, BigDecimal.valueOf(72), 41.010, 28.980, now, now));
        store.getLatestStates().put(102L,
                new LatestState(102L, WasteType.CAM, BigDecimal.valueOf(65), 41.012, 28.986, now, now));
        store.getLatestStates().put(103L,
                new LatestState(103L, WasteType.CAM, BigDecimal.valueOf(81), 41.016, 28.992, now, now));
        store.getLatestStates().put(104L,
                new LatestState(104L, WasteType.PLASTIK, BigDecimal.valueOf(70), 41.018, 28.975, now, now));
        store.getLatestStates().put(105L,
                new LatestState(105L, WasteType.PLASTIK, BigDecimal.valueOf(63), 41.020, 28.982, now, now));
        store.getLatestStates().put(106L,
                new LatestState(106L, WasteType.PLASTIK, BigDecimal.valueOf(79), 41.022, 28.988, now, now));
        store.getLatestStates().put(107L,
                new LatestState(107L, WasteType.KAGIT, BigDecimal.valueOf(68), 41.014, 28.970, now, now));
        store.getLatestStates().put(108L,
                new LatestState(108L, WasteType.KAGIT, BigDecimal.valueOf(74), 41.017, 28.976, now, now));
        store.getLatestStates().put(109L,
                new LatestState(109L, WasteType.KAGIT, BigDecimal.valueOf(66), 41.019, 28.983, now, now));
        store.getLatestStates().put(110L,
                new LatestState(110L, WasteType.IKINCI_EL_ESYA, BigDecimal.valueOf(71), 41.023, 28.978, now, now));
        store.getLatestStates().put(111L,
                new LatestState(111L, WasteType.IKINCI_EL_ESYA, BigDecimal.valueOf(69), 41.025, 28.984, now, now));
        store.getLatestStates().put(112L,
                new LatestState(112L, WasteType.IKINCI_EL_ESYA, BigDecimal.valueOf(77), 41.027, 28.990, now, now));
        store.getLatestStates().put(113L,
                new LatestState(113L, WasteType.METAL, BigDecimal.valueOf(80), 41.011, 28.972, now, now));
        store.getLatestStates().put(114L,
                new LatestState(114L, WasteType.METAL, BigDecimal.valueOf(62), 41.013, 28.978, now, now));
        store.getLatestStates().put(115L,
                new LatestState(115L, WasteType.METAL, BigDecimal.valueOf(85), 41.015, 28.985, now, now));

        seedKahramanmarasDemo(now);
    }

    private void seedKahramanmarasDemo(OffsetDateTime now) {
        WasteType[] types = {
                WasteType.CAM,
                WasteType.PLASTIK,
                WasteType.KAGIT,
                WasteType.IKINCI_EL_ESYA,
                WasteType.METAL
        };

        Map<WasteType, Long> baseIdByType = Map.of(
                WasteType.CAM, 10000L,
                WasteType.PLASTIK, 20000L,
                WasteType.KAGIT, 30000L,
                WasteType.IKINCI_EL_ESYA, 40000L,
                WasteType.METAL, 50000L
        );

        int rows = 25;
        int cols = 10;
        double latMin = 37.45;
        double latMax = 37.70;
        double lngMin = 36.80;
        double lngMax = 37.10;
        double typeOffset = 0.0002;

        int fillMin = 60;
        int fillMax = 95;
        int span = fillMax - fillMin + 1;

        double latStep = rows > 1 ? (latMax - latMin) / (rows - 1) : 0.0;
        double lngStep = cols > 1 ? (lngMax - lngMin) / (cols - 1) : 0.0;

        for (int typeIndex = 0; typeIndex < types.length; typeIndex++) {
            WasteType wasteType = types[typeIndex];
            long baseId = baseIdByType.get(wasteType);

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int idx = r * cols + c + 1;
                    long id = baseId + idx;

                    double lat = latMin + (latStep * r) + (typeOffset * typeIndex);
                    double lng = lngMin + (lngStep * c) + (typeOffset * typeIndex);
                    int fill = fillMin + ((r * 7 + c * 11 + typeIndex * 5) % span);

                    store.getContainers().put(id,
                            new Container(id, wasteType, lat, lng, "ACTIVE", now));
                    store.getLatestStates().put(id,
                            new LatestState(id, wasteType, BigDecimal.valueOf(fill), lat, lng, now, now));
                }
            }
        }
    }
}