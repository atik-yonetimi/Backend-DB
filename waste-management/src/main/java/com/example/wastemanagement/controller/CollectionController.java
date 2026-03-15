package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.collection.CollectionCreateRequest;
import com.example.wastemanagement.dto.collection.CollectionResponse;
import com.example.wastemanagement.entity.Driver;
import com.example.wastemanagement.security.TokenService;
import com.example.wastemanagement.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;
    private final TokenService tokenService;

    public CollectionController(CollectionService collectionService, TokenService tokenService) {
        this.collectionService = collectionService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public CollectionResponse create(@Valid @RequestBody CollectionCreateRequest request,
                                     @RequestHeader("Authorization") String authorizationHeader) {
        Driver driver = tokenService.getDriverFromAuthorizationHeader(authorizationHeader);
        return collectionService.createCollection(request, driver);
    }
}