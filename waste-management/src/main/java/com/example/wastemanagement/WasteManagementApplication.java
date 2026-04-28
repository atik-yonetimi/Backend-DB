package com.example.wastemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // 🚨 BUNU EKLE

@SpringBootApplication
@EnableScheduling // 🚨 BUNU EKLE (Zamanlanmış görevleri başlatır)
public class WasteManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(WasteManagementApplication.class, args);
    }
}