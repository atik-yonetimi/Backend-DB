package com.example.wastemanagement.security;

import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final DriverRepository driverRepository;
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();

    public TokenService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public String generateToken(Long driverId) {
        String token = "token-" + driverId + "-" + UUID.randomUUID().toString().substring(0, 8);
        tokens.put(token, driverId);
        return token;
    }

    public Driver getDriverFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Gecersiz veya eksik token");
        }

        String token = authorizationHeader.substring(7);
        Long driverId = tokens.get(token);

        if (driverId == null) {
            throw new IllegalArgumentException("Token gecersiz");
        }

        return driverRepository.findById(driverId)
                .orElseThrow(() -> new NotFoundException("Surucu bulunamadi"));
    }
}