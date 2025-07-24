package com.example.sfera_backend.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CourseDetailsResponse (
        UUID id,
        String name,
        String description,
        int duration,
        String videoUrl,
        String imageUrl,
        BigDecimal price
){
}
