package com.example.sfera_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TeacherDTO (
        @NotBlank(message = "Ism bo'sh bo'lmasin")
        String fullName,

        @NotBlank(message = "Telefon raqam bo'sh bo'lmasin")
        String phoneNumber,

        @NotBlank(message = "Biografiya bo'sh bo'lmasin")
        String biography,

        @Schema(hidden = true)
        String imageUrl
){
}
