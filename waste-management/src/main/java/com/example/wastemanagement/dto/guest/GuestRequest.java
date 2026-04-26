package com.example.wastemanagement.dto.guest;

import jakarta.validation.constraints.Size;

public class GuestRequest {

    @Size(max = 100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}