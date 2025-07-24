package com.example.sfera_backend.dto.response;

import java.time.LocalDateTime;

public record LeadResponse(
        String fullName,
        String phoneNumber,
        LocalDateTime date
) {
}
