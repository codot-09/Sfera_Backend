package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.request.StudentRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.StudentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface StudentService {
    ApiResponse<String> addStudent(StudentRequest studentRequest);
    ApiResponse<String> updateStudent(UUID studentId,StudentRequest studentRequest);
    ApiResponse<String> deleteStudent(UUID studentId);
    ApiResponse<List<StudentResponse>> getStudentList();
    ApiResponse<StudentResponse> getStudentById(UUID studentId);
}
