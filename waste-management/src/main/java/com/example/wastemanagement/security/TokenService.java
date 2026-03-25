package com.example.wastemanagement.security;

import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.InMemoryStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final InMemoryStore store;

    public TokenService(InMemoryStore store) {
        this.store = store;
    }

    public String generateToken(Long driverId) {
        String token = "token-" + driverId + "-" + UUID.randomUUID().toString().substring(0, 8);
        store.getTokens().put(token, driverId);
        return token;
    }

    public Driver getDriverFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Gecersiz veya eksik token");
        }

        String token = authorizationHeader.substring(7);
        Long driverId = store.getTokens().get(token);

        if (driverId == null) {
            throw new IllegalArgumentException("Token gecersiz");
        }

        Driver driver = store.getDrivers().get(driverId);
        if (driver == null) {
            throw new NotFoundException("Surucu bulunamadi");
        }

        return driver;
    }
}