package com.example.wastemanagement.dto.weekly;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class WeeklyWasteTotalResponse {

    private String wasteType;
    private BigDecimal totalKg;
    private OffsetDateTime weekStart;
    private OffsetDateTime weekEnd;
    private OffsetDateTime lastUpdated;

    public WeeklyWasteTotalResponse(String wasteType, BigDecimal totalKg,
                                    OffsetDateTime weekStart,
                                    OffsetDateTime weekEnd,
                                    OffsetDateTime lastUpdated) {
        this.wasteType = wasteType;
        this.totalKg = totalKg;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.lastUpdated = lastUpdated;
    }

    public String getWasteType() {
        return wasteType;
    }

    public BigDecimal getTotalKg() {
        return totalKg;
    }

    public OffsetDateTime getWeekStart() {
        return weekStart;
    }

    public OffsetDateTime getWeekEnd() {
        return weekEnd;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }
}