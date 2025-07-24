package com.example.sfera_backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;


public record EventDTO(
        UUID id,

        String name,

        String description,

        String imageUrl,

        LocalDate date,

        String hour
) {

}
