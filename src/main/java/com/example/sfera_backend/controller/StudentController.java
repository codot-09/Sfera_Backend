package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.StudentRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.StudentResponse;
import com.example.sfera_backend.jwt.RequireToken;
import com.example.sfera_backend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;


    @GetMapping
    @Operation(
            summary = "Studentlarni kurish uchun"
    )
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(studentService.getStudentList());
    }



    @GetMapping("/{studentId}")
    @Operation(
            summary = "Studentlarni bittasini kurish uchun"
    )
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable UUID studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }



    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Yangi student qushish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> createStudent(
            @RequestParam("file") MultipartFile file,
            @RequestBody StudentRequest studentRequest) throws IOException {
        return ResponseEntity.ok(studentService.addStudent(studentRequest, file));
    }



    @PutMapping(value = "/{studentId}", consumes = "multipart/form-data")
    @Operation(
            summary = "Student tahrirlash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> updateStudent(
            @PathVariable UUID studentId,
            @RequestParam("file") MultipartFile file,
            @RequestBody StudentRequest studentRequest) throws IOException {
        return ResponseEntity.ok(studentService.updateStudent(studentId, studentRequest, file));
    }




    @DeleteMapping("/{studentId}")
    @Operation(
            summary = "Studentni uchirish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @RequireToken
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable UUID studentId) {
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }
}
