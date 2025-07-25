package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.CourseRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.CourseDetailsResponse;
import com.example.sfera_backend.jwt.RequireToken;
import com.example.sfera_backend.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Tag(name = "Course", description = "Course API")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    @Operation(
            summary = "Yangi kurs yaratish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> createCourse(
            @RequestBody CourseRequest request
    ) throws IOException {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "ID bo'yicha kursni ko'rish"
    )
    public ResponseEntity<ApiResponse<CourseDetailsResponse>> getById(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping
    @Operation(
            summary = "Barcha kurslarni ko'rish"
    )
    public ResponseEntity<ApiResponse<List<CourseDetailsResponse>>> getAll(
            @RequestParam(defaultValue = "true") boolean status
    ){
        return ResponseEntity.ok(courseService.getAll(status));
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "kurs malumotlarini yangilash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> updateCourse(
            @PathVariable UUID id,
            @RequestBody CourseRequest request
    ) throws IOException {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Kursni o'chirish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }
}
