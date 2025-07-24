package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.TeacherDTO;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.service.TeacherService;
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
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Tag(name = "Teacher", description = "Teacher API")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping(consumes = "multipart/form-data" )
    @Operation(
            summary = "O'qituvchi qo'shish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN")
    public ResponseEntity<ApiResponse<String>> createTeacher(
            @RequestParam("file") MultipartFile file,
            @RequestBody TeacherDTO request
    ) throws IOException {
        return ResponseEntity.ok(teacherService.createTeacher(file,request));
    }

    @PutMapping(value = "/{id}",consumes = "multipart/form-data" )
    @Operation(
            summary = "O'qituvchi ma'lumotlarini yangilash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateTeacher(
            @PathVariable("id") UUID id,
            @RequestBody TeacherDTO teacherDTO,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(teacherService.updateTeacher(file,teacherDTO,id));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "ID bo'yicha o'qituvchini ko'rish"
    )
    public ResponseEntity<ApiResponse<TeacherDTO>> getById(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(teacherService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "O'qituvchini o'chirish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteTeacher(
            @PathVariable("id") UUID id
    ){
        return ResponseEntity.ok(teacherService.deleteTeacher(id));
    }

    @GetMapping
    @Operation(
            summary = "Barcha o'qituvchilarni ko'rish"
    )
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getAll(){
        return ResponseEntity.ok(teacherService.getAll());
    }
}
