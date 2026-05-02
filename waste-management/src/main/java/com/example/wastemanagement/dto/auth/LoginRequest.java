package com.example.wastemanagement.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Plaka boş olamaz")
    private String plate;

    // 🚨 YENİ EKLENEN: Backend artık Flutter'dan gelen şifreyi de karşılayabilecek
    @NotBlank(message = "Şifre boş olamaz")
    private String password;

    // --- GETTER VE SETTER'LAR ---
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}