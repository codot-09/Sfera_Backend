package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.TeacherDTO;
import com.example.sfera_backend.dto.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TeacherService {
    ApiResponse<String> createTeacher(TeacherDTO teacherDTO) throws IOException;
    ApiResponse<TeacherDTO> getById(UUID id);
    ApiResponse<String> updateTeacher(TeacherDTO teacherDTO,UUID teacherId) throws IOException;
    ApiResponse<String> deleteTeacher(UUID id);
    ApiResponse<List<TeacherDTO>> getAll(boolean status);
}
