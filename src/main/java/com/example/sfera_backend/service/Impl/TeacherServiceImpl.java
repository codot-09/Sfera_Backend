package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.TeacherDTO;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.entity.Teacher;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.TeacherMapper;
import com.example.sfera_backend.repository.TeacherRepository;
import com.example.sfera_backend.service.TeacherService;
import com.example.sfera_backend.service.cloud.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final CloudService cloudService;
    private final TeacherMapper mapper;

    @Override
    public ApiResponse<String> createTeacher(MultipartFile file, TeacherDTO request) throws IOException {
        if(teacherRepository.existsByPhone(request.phoneNumber())){
            return ApiResponse.error("O'qituvchi allaqachon mavjud");
        }

        String imageUrl = cloudService.uploadFile(file);

        Teacher teacher = Teacher.builder()
                .fullName(request.fullName())
                .phone(request.phoneNumber())
                .biography(request.biography())
                .imageUrl(imageUrl)
                .build();

        teacherRepository.save(teacher);

        return ApiResponse.success("O'qituvchi saqlandi");
    }

    @Override
    public ApiResponse<TeacherDTO> getById(UUID id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O'qituvchi topilmadi"));

        return ApiResponse.success(mapper.toDTO(teacher));
    }

    @Override
    public ApiResponse<String> updateTeacher(MultipartFile file,TeacherDTO teacherDTO, UUID teacherId) throws IOException {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("O'qituvchi topilmadi"));

        String imageUrl = cloudService.uploadFile(file);

        teacher.setFullName(teacherDTO.fullName());
        teacher.setPhone(teacherDTO.phoneNumber());
        teacher.setBiography(teacherDTO.biography());
        teacher.setImageUrl(imageUrl);

        teacherRepository.save(teacher);

        return ApiResponse.success("O'qituvchi ma'lumotlari yangilandi");
    }

    @Override
    public ApiResponse<String> deleteTeacher(UUID id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O'qituvchi topilmadi"));

        teacherRepository.delete(teacher);

        return ApiResponse.success("O'qituvchi o'chirildi");
    }

    @Override
    public ApiResponse<List<TeacherDTO>> getAll() {
        List<Teacher> teachers = teacherRepository.findAll();

        return ApiResponse.success(mapper.toDTOList(teachers));
    }
}
