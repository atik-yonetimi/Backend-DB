package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.weekly.WeeklyWasteTotalResponse;
import com.example.wastemanagement.service.WeeklyWasteTotalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekly-waste-totals")
public class WeeklyWasteTotalController {

    private final WeeklyWasteTotalService service;

    public WeeklyWasteTotalController(WeeklyWasteTotalService service) {
        this.service = service;
    }

    @GetMapping
    public List<WeeklyWasteTotalResponse> getAll() {
        return service.getAll();
    }

    @PostMapping("/reset")
    public void reset() {
        service.reset();
    }
}