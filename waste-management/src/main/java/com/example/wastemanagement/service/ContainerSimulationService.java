package com.example.wastemanagement.service;

import com.example.wastemanagement.entity.Container;
import com.example.wastemanagement.entity.TelemetryRecord;
import com.example.wastemanagement.repository.ContainerRepository;
import com.example.wastemanagement.repository.TelemetryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ContainerSimulationService {

    private final ContainerRepository containerRepository;
    private final TelemetryRepository telemetryRepository;
    private final Random random = new Random();

    public ContainerSimulationService(ContainerRepository containerRepository, TelemetryRepository telemetryRepository) {
        this.containerRepository = containerRepository;
        this.telemetryRepository = telemetryRepository;
    }

    // Her 1 dakikada (60000ms) bir otomatik tetiklenir
    @Scheduled(fixedRate = 60000)
    public void simulateWasteFilling() {
        List<Container> allContainers = containerRepository.findAll();
        List<TelemetryRecord> allTelemetry = telemetryRepository.findAll();

        // Her konteynerin en son telemetri kaydını bul
        Map<Long, TelemetryRecord> latestByContainer = allTelemetry.stream()
                .collect(Collectors.toMap(
                        TelemetryRecord::getContainerId,
                        t -> t,
                        (t1, t2) -> t1.getSourceTimestamp().isAfter(t2.getSourceTimestamp()) ? t1 : t2
                ));

        for (Container container : allContainers) {
            TelemetryRecord lastRecord = latestByContainer.get(container.getId());
            
            if (lastRecord != null) {
                double currentFill = lastRecord.getFillPercent().doubleValue();

                // Sadece %100'den küçükse doldurmaya devam et
                if (currentFill < 100.0) {
                    // Dakikada %2.0 ile %5.0 arası rastgele dolum
                    double addedWaste = 2.0 + (3.0 * random.nextDouble());
                    double newFill = Math.min(currentFill + addedWaste, 100.0);

                    // Yeni doluluk oranıyla yeni bir telemetri (sensör) kaydı at
                    TelemetryRecord newRecord = new TelemetryRecord(
                            null, // id
                            container.getId(), // containerId
                            BigDecimal.valueOf(newFill), // fillPercent
                            lastRecord.getLat(), // lat
                            lastRecord.getLng(), // lng
                            OffsetDateTime.now(), // sourceTimestamp
                            OffsetDateTime.now()  // 🚨 EKLENEN 7. PARAMETRE (ingestedAt)
                    );
                    telemetryRepository.save(newRecord);
                }
            }
        }
        System.out.println("♻️ Simülasyon: Konteynerlere çöp eklendi (Sensör verileri güncellendi).");
    }
}