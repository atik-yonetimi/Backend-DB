package com.example.wastemanagement.controller;

import com.example.wastemanagement.entity.SkippedAlert;
import com.example.wastemanagement.repository.SkippedAlertRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/alerts")
public class SkippedAlertController {

    private final SkippedAlertRepository skippedAlertRepository;

    public SkippedAlertController(SkippedAlertRepository skippedAlertRepository) {
        this.skippedAlertRepository = skippedAlertRepository;
    }

    // 1. Tüm atlananları listele (Web Paneli burayı çağırıp ekrana çizer)
    @GetMapping
    public List<SkippedAlert> getAllAlerts() {
        return skippedAlertRepository.findAll();
    }

    // 2. Yönetici okuduğunda/çözdüğünde sil (Web Paneli çöp kutusuna basınca burayı çağırır)
    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable Long id) {
        skippedAlertRepository.deleteById(id);
        System.out.println("✅ Yönetici bildirimi sildi: ID " + id);
    }
}