package com.example.wastemanagement.service;

import com.example.wastemanagement.dto.complaint.ComplaintRequest;
import com.example.wastemanagement.dto.complaint.ComplaintResponse;
import com.example.wastemanagement.entity.Complaint;
import com.example.wastemanagement.exception.NotFoundException;
import com.example.wastemanagement.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public ComplaintResponse create(ComplaintRequest request) {
        Complaint complaint = new Complaint();
        complaint.setGuestName(request.getGuestName());
        complaint.setGuestEmail(request.getGuestEmail());
        complaint.setSubject(request.getSubject());
        complaint.setMessage(request.getMessage());

        Complaint saved = complaintRepository.save(complaint);

        return toResponse(saved);
    }

    public List<ComplaintResponse> getAll() {
        return complaintRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!complaintRepository.existsById(id)) {
            throw new NotFoundException("Sikayet bulunamadi");
        }

        complaintRepository.deleteById(id);
    }

    private ComplaintResponse toResponse(Complaint complaint) {
        return new ComplaintResponse(
                complaint.getId(),
                complaint.getGuestName(),
                complaint.getGuestEmail(),
                complaint.getSubject(),
                complaint.getMessage(),
                complaint.getCreatedAt()
        );
    }
}