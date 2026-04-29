package com.example.wastemanagement.controller;

import com.example.wastemanagement.entity.SkippedAlert;
import com.example.wastemanagement.repository.SkippedAlertRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/alerts") // 🚨 BURASI DÜZELTİLDİ (/api eklendi)
@CrossOrigin("*") // 🚨 Chrome üzerinden (Web) sorunsuz erişim için eklendi
public class SkippedAlertController {

    private final SkippedAlertRepository skippedAlertRepository;

    public SkippedAlertController(SkippedAlertRepository skippedAlertRepository) {
        this.skippedAlertRepository = skippedAlertRepository;
    }

    // Tüm atlananları listele
    @GetMapping
    public List<SkippedAlert> getAllAlerts() {
        return skippedAlertRepository.findAll();
    }

    // Yönetici sildiğinde
    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable Long id) {
        skippedAlertRepository.deleteById(id);
        System.out.println("✅ Yönetici bildirimi sildi: ID " + id);
    }
}