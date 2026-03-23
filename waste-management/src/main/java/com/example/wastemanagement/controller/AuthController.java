package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.auth.LoginRequest;
import com.example.wastemanagement.dto.auth.LoginResponse;
import com.example.wastemanagement.dto.auth.MeResponse;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.security.TokenService;
import com.example.wastemanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.loginByPlate(request.getPlate());
    }

    @GetMapping("/me")
    public MeResponse me(@RequestHeader("Authorization") String authorizationHeader) {
        Driver driver = tokenService.getDriverFromAuthorizationHeader(authorizationHeader);
        return authService.me(driver);
    }
}