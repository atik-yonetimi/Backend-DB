package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.guest.GuestRequest;
import com.example.wastemanagement.dto.guest.GuestResponse;
import com.example.wastemanagement.entity.Guest;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public GuestResponse create(GuestRequest request) {
        Guest guest = new Guest();
        guest.setName(request.getName());

        Guest saved = guestRepository.save(guest);
        return toResponse(saved);
    }

    public List<GuestResponse> getAll() {
        return guestRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new NotFoundException("Misafir bulunamadi");
        }

        guestRepository.deleteById(id);
    }

    private GuestResponse toResponse(Guest guest) {
        return new GuestResponse(
                guest.getId(),
                guest.getName(),
                guest.getCreatedAt()
        );
    }
}