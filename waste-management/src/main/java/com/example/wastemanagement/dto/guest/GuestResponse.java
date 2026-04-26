package com.example.wastemanagement.dto.guest;

import java.time.OffsetDateTime;

public class GuestResponse {

    private Long id;
    private String name;
    private OffsetDateTime createdAt;

    public GuestResponse(Long id, String name, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}