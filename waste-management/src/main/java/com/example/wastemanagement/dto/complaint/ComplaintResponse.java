package com.example.wastemanagement.dto.complaint;

import java.time.OffsetDateTime;

public class ComplaintResponse {

    private Long id;
    private String guestName;
    private String guestEmail;
    private String subject;
    private String message;
    private OffsetDateTime createdAt;

    public ComplaintResponse(Long id, String guestName, String guestEmail,
                             String subject, String message, OffsetDateTime createdAt) {
        this.id = id;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.subject = subject;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}