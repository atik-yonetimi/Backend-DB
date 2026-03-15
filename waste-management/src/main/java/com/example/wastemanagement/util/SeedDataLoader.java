package com.example.wastemanagement.util;

import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.entity.LatestState;
import com.example.wastemanagement.entity.Vehicle;
import com.example.wastemanagement.enums.WasteType;
import com.example.wastemanagement.repository.InMemoryStore;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

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

        store.getVehicles().put("veh_glass",
                new Vehicle("veh_glass", "34GLS001", WasteType.GLASS, 41.0082, 28.9784, true));
        store.getVehicles().put("veh_plastic",
                new Vehicle("veh_plastic", "34PLS001", WasteType.PLASTIC, 41.0082, 28.9784, true));
        store.getVehicles().put("veh_paper",
                new Vehicle("veh_paper", "34PPR001", WasteType.PAPER, 41.0082, 28.9784, true));
        store.getVehicles().put("veh_second",
                new Vehicle("veh_second", "34SHD001", WasteType.SECOND_HAND, 41.0082, 28.9784, true));
        store.getVehicles().put("veh_metal",
                new Vehicle("veh_metal", "34MTL001", WasteType.METAL, 41.0082, 28.9784, true));

        store.getDrivers().put("drv_1",
                new Driver("drv_1", "Elif Cam", "veh_glass", true));
        store.getDrivers().put("drv_2",
                new Driver("drv_2", "Ali Plastik", "veh_plastic", true));
        store.getDrivers().put("drv_3",
                new Driver("drv_3", "Ayse Kagit", "veh_paper", true));
        store.getDrivers().put("drv_4",
                new Driver("drv_4", "Mert Ikinci El", "veh_second", true));
        store.getDrivers().put("drv_5",
                new Driver("drv_5", "Zeynep Metal", "veh_metal", true));

        store.getContainers().put("cnt_1",
                new Container("cnt_1", "GLS-001", WasteType.GLASS, 41.015, 28.984, true));
        store.getContainers().put("cnt_2",
                new Container("cnt_2", "GLS-002", WasteType.GLASS, 41.021, 28.991, true));
        store.getContainers().put("cnt_3",
                new Container("cnt_3", "GLS-003", WasteType.GLASS, 41.027, 28.998, true));
        store.getContainers().put("cnt_4",
                new Container("cnt_4", "PLS-001", WasteType.PLASTIC, 41.011, 28.986, true));
        store.getContainers().put("cnt_5",
                new Container("cnt_5", "PLS-002", WasteType.PLASTIC, 41.016, 28.995, true));
        store.getContainers().put("cnt_6",
                new Container("cnt_6", "PPR-001", WasteType.PAPER, 41.013, 28.981, true));
        store.getContainers().put("cnt_7",
                new Container("cnt_7", "MTL-001", WasteType.METAL, 41.017, 28.992, true));

        OffsetDateTime now = OffsetDateTime.now();

        store.getLatestStates().put("cnt_1",
                new LatestState("cnt_1", WasteType.GLASS, 78, 41.015, 28.984, now));
        store.getLatestStates().put("cnt_2",
                new LatestState("cnt_2", WasteType.GLASS, 64, 41.021, 28.991, now));
        store.getLatestStates().put("cnt_3",
                new LatestState("cnt_3", WasteType.GLASS, 42, 41.027, 28.998, now));
        store.getLatestStates().put("cnt_4",
                new LatestState("cnt_4", WasteType.PLASTIC, 82, 41.011, 28.986, now));
        store.getLatestStates().put("cnt_5",
                new LatestState("cnt_5", WasteType.PLASTIC, 61, 41.016, 28.995, now));
        store.getLatestStates().put("cnt_6",
                new LatestState("cnt_6", WasteType.PAPER, 58, 41.013, 28.981, now));
        store.getLatestStates().put("cnt_7",
                new LatestState("cnt_7", WasteType.METAL, 88, 41.017, 28.992, now));
    }
}