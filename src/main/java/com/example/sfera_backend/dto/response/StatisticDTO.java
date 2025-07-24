package com.example.sfera_backend.dto.response;

import lombok.Builder;

@Builder
public record StatisticDTO(
        long courseCount,
        long teacherCount,
        long studentCount,
        long groupCount
) {
}
