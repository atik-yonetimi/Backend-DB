package com.example.wastemanagement.controller;

import com.example.wastemanagement.dto.complaint.ComplaintRequest;
import com.example.wastemanagement.dto.complaint.ComplaintResponse;
import com.example.wastemanagement.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public ComplaintResponse create(@Valid @RequestBody ComplaintRequest request) {
        return complaintService.create(request);
    }

    @GetMapping
    public List<ComplaintResponse> getAll() {
        return complaintService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        complaintService.delete(id);
    }
}