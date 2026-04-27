package com.example.wastemanagement.dto.auth;

public class AdminLoginResponse {

    private Long adminId;
    private String username;
    private String role;
    private String accessToken; // 🚨 EKLENEN KRİTİK KISIM

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // 🚨 TOKEN İÇİN GETTER VE SETTER EKLENDİ 🚨
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}