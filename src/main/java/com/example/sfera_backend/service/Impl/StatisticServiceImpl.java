package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.StatisticDTO;
import com.example.sfera_backend.repository.CourseRepository;
import com.example.sfera_backend.repository.GroupRepository;
import com.example.sfera_backend.repository.StudentRepository;
import com.example.sfera_backend.repository.TeacherRepository;
import com.example.sfera_backend.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Override
    public ApiResponse<StatisticDTO> getStatistic() {
        long course = courseRepository.count();
        long teacher = teacherRepository.count();
        long group = groupRepository.count();
        long student = studentRepository.count();
        StatisticDTO statisticDTO = StatisticDTO.builder()
                .studentCount(student)
                .groupCount(group)
                .courseCount(course)
                .teacherCount(teacher)
                .build();
        return ApiResponse.success(statisticDTO);
    }
}
