package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.request.StudentRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.StudentResponse;
import com.example.sfera_backend.entity.Course;
import com.example.sfera_backend.entity.Student;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.StudentMapper;
import com.example.sfera_backend.repository.CourseRepository;
import com.example.sfera_backend.repository.StudentRepository;
import com.example.sfera_backend.service.StudentService;
import com.example.sfera_backend.service.cloud.CloudServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;


    @Override
    public ApiResponse<String> addStudent(StudentRequest studentRequest) {
        boolean b = studentRepository.existsByFullName(studentRequest.getUserName());
        if (b) {
            return ApiResponse.error("Student already exists");
        }

        Student student = Student.builder()
                .fullName(studentRequest.getUserName())
                .description(studentRequest.getDescription())
                .phoneNumber(studentRequest.getPhoneNumber())
                .company(studentRequest.getCompany())
                .job(studentRequest.getJob())
                .fileUrl(studentRequest.getFileUrl())
                .build();
        studentRepository.save(student);
        return ApiResponse.success("Student saqlandi");
    }

    @Override
    public ApiResponse<String> updateStudent(UUID studentId,StudentRequest studentRequest) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found")
        );

        boolean b = studentRepository.existsByFullNameAndIdNot(student.getFullName(), studentId);
        if (b) {
            return ApiResponse.error("Student already exists");
        }

        student.setId(studentId);
        student.setJob(studentRequest.getJob());
        student.setCompany(studentRequest.getCompany());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setFileUrl(studentRequest.getFileUrl());
        student.setDescription(studentRequest.getDescription());
        student.setFullName(studentRequest.getUserName());
        studentRepository.save(student);

        return ApiResponse.success("Student tahrirlandi");
    }

    @Override
    public ApiResponse<String> deleteStudent(UUID studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found")
        );

        studentRepository.delete(student);

        return ApiResponse.success("Student o'chirildi");
    }

    @Override
    public ApiResponse<List<StudentResponse>> getStudentList() {
        List<StudentResponse> list = studentRepository.findAll()
                .stream().map(studentMapper::toResponseStudent).toList();
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<StudentResponse> getStudentById(UUID studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found")
        );

        return ApiResponse.success(studentMapper.toResponseStudent(student));
    }
}
