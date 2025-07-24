package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.response.CourseDetailsResponse;
import com.example.sfera_backend.entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {
    public CourseDetailsResponse toResponse(Course course){
        return new CourseDetailsResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getDuration(),
                course.getVideoUrl(),
                course.getImageUrl(),
                course.getPrice()
        );
    }

    public List<CourseDetailsResponse> toResponseList(List<Course> courses){
        return courses.stream().map(this::toResponse).toList();
    }
}
