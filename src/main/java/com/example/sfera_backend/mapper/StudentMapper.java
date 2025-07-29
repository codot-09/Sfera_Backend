package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.response.StudentResponse;
import com.example.sfera_backend.entity.Student;
import com.example.sfera_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponse toResponseStudent(Student student){
        return new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getDescription(),
                student.getJob(),
                student.getCompany(),
                student.getPhoneNumber(),
                student.getFileUrl()
        );
    }
}
