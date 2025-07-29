package com.example.sfera_backend.dto.response;

import java.util.UUID;

public record StudentResponse(
        UUID id,
        String fullName,
        String description,
        String job,
        String company,
        String phoneNumber,
        String fileUrl
) {
}
