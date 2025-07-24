package com.example.sfera_backend.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record GroupResponse (
        UUID id,
        String name,
        String description,
        LocalDate openDate,
        String course,
        String imageUrl,
        int leftStudents,
        String teacherName
){
}
