package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.weekly.WeeklyWasteTotalResponse;
import com.example.wastemanagement.entity.WeeklyWasteTotal;
import com.example.wastemanagement.repository.WeeklyWasteTotalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyWasteTotalService {

    private final WeeklyWasteTotalRepository repository;

    public WeeklyWasteTotalService(WeeklyWasteTotalRepository repository) {
        this.repository = repository;
    }

    public List<WeeklyWasteTotalResponse> getAll() {
        return repository.findAllByOrderByWasteTypeAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    
    
    public void reset() {
        repository.resetTotals();
    }

    private WeeklyWasteTotalResponse toResponse(WeeklyWasteTotal e) {
        return new WeeklyWasteTotalResponse(
                e.getWasteType(),
                e.getTotalKg(),
                e.getWeekStart(),
                e.getWeekEnd(),
                e.getLastUpdated()
        );
    }
}