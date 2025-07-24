package com.example.sfera_backend.dto.response;

import java.util.UUID;

public record StudentResponse(
        UUID id,
        String fullName,
        double score,
        UUID courseId,
        String fileUrl
) {
}
