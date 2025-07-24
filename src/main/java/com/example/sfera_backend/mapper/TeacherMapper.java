package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.TeacherDTO;
import com.example.sfera_backend.entity.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapper {
    public TeacherDTO toDTO(Teacher teacher){
        return new TeacherDTO(
                teacher.getFullName(),
                teacher.getPhone(),
                teacher.getBiography(),
                teacher.getImageUrl()
        );
    }

    public List<TeacherDTO> toDTOList(List<Teacher> teachers){
        return teachers.stream().map(this::toDTO).toList();
    }
}
