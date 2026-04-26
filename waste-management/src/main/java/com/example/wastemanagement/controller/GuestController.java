package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.guest.GuestRequest;
import com.example.wastemanagement.dto.guest.GuestResponse;
import com.example.wastemanagement.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public GuestResponse create(@Valid @RequestBody GuestRequest request) {
        return guestService.create(request);
    }

    @GetMapping
    public List<GuestResponse> getAll() {
        return guestService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        guestService.delete(id);
    }
}