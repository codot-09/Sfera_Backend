package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.request.CourseRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.CourseDetailsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CourseService {
    ApiResponse<String> createCourse(CourseRequest request, MultipartFile file) throws IOException;
    ApiResponse<CourseDetailsResponse> getById(UUID id);
    ApiResponse<List<CourseDetailsResponse>> getAll();
    ApiResponse<String> updateCourse(UUID id, CourseRequest request,MultipartFile file) throws IOException;
    ApiResponse<String> deleteCourse(UUID id);
}
