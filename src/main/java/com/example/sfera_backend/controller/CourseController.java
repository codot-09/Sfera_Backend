package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.CourseRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.CourseDetailsResponse;
import com.example.sfera_backend.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Tag(name = "Course", description = "Course API")
public class CourseController {
    private final CourseService courseService;

    @PostMapping(consumes = "multipart/form-data" )
    @Operation(
            summary = "Yangi kurs yaratish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> createCourse(
            @RequestBody CourseRequest request,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(courseService.createCourse(request, file));
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
    public ResponseEntity<ApiResponse<List<CourseDetailsResponse>>> getAll(){
        return ResponseEntity.ok(courseService.getAll());
    }

    @PutMapping(value = "/{id}",consumes = "multipart/form-data" )
    @Operation(
            summary = "kurs malumotlarini yangilash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateCourse(
            @PathVariable UUID id,
            @RequestBody CourseRequest request,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(courseService.updateCourse(id, request, file));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Kursni o'chirish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }
}
