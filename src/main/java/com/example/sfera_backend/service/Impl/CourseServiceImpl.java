package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.request.CourseRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.CourseDetailsResponse;
import com.example.sfera_backend.entity.Course;
import com.example.sfera_backend.entity.Teacher;
import com.example.sfera_backend.entity.User;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.CourseMapper;
import com.example.sfera_backend.repository.CourseRepository;
import com.example.sfera_backend.repository.TeacherRepository;
import com.example.sfera_backend.repository.UserRepository;
import com.example.sfera_backend.service.CourseService;
import com.example.sfera_backend.service.cloud.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CloudService cloudService;
    private final CourseMapper mapper;
    private final TeacherRepository teacherRepository;

    @Override
    public ApiResponse<String> createCourse(CourseRequest request) throws IOException {
        if(courseRepository.existsByName(request.getName())){
            return ApiResponse.error("Kurs allaqachon mavjud");
        }

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("O'qituvchi topilmadi"));

        if(!teacher.isActive()){
            return ApiResponse.error("O'qituvchi mavjud emas");
        }

        Course newCourse = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .duration(request.getDuration())
                .videoUrl(request.getVideoUrl())
                .price(request.getPrice())
                .teacher(teacher)
                .imageUrl(request.getImageUrl())
                .build();

        courseRepository.save(newCourse);
        return ApiResponse.success("Kurs muvaffaqiyatli yaratildi");
    }

    @Override
    public ApiResponse<CourseDetailsResponse> getById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurs topilmadi"));

        return ApiResponse.success(mapper.toResponse(course));
    }

    @Override
    public ApiResponse<List<CourseDetailsResponse>> getAll(boolean status) {
        List<Course> courses = courseRepository.findAllByActive(status);

        return ApiResponse.success(mapper.toResponseList(courses));
    }

    @Override
    public ApiResponse<String> updateCourse(UUID id, CourseRequest request) throws IOException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurs topilmadi"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("O'qituvchi topilmadi"));

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setDuration(request.getDuration());
        course.setPrice(request.getPrice());
        course.setVideoUrl(request.getVideoUrl());
        course.setImageUrl(request.getImageUrl());
        course.setTeacher(teacher);

        courseRepository.save(course);

        return ApiResponse.success("Kurs muvaffaqiyatli yukandildi");
    }

    @Override
    public ApiResponse<String> deleteCourse(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurs topilmadi"));

        course.setActive(false);
        courseRepository.save(course);

        return ApiResponse.success("Kurs muvaffaqiyatli o'chirildi");
    }
}
