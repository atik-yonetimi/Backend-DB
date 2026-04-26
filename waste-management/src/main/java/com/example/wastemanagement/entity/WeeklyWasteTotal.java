package com.example.wastemanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "weekly_waste_totals")
public class WeeklyWasteTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "waste_type", nullable = false)
    private String wasteType;

    @Column(name = "total_kg", nullable = false)
    private BigDecimal totalKg;

    @Column(name = "week_start", nullable = false)
    private OffsetDateTime weekStart;

    @Column(name = "week_end", nullable = false)
    private OffsetDateTime weekEnd;

    @Column(name = "last_updated", nullable = false)
    private OffsetDateTime lastUpdated;

    public Long getId() {
        return id;
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