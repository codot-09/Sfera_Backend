package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.request.GroupRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.GroupResponse;
import com.example.sfera_backend.entity.Course;
import com.example.sfera_backend.entity.Group;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.GroupMapper;
import com.example.sfera_backend.repository.CourseRepository;
import com.example.sfera_backend.repository.GroupRepository;
import com.example.sfera_backend.service.GroupService;
import com.example.sfera_backend.service.cloud.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final CloudService cloudService;
    private final GroupMapper mapper;

    @Override
    public ApiResponse<String> openGroup(MultipartFile file,GroupRequest request) throws IOException {
        if(groupRepository.existsByName(request.getName())){
            return ApiResponse.error("Guruh mavjud");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Kurs topilmadi"));

        String imageUrl = cloudService.uploadFile(file);

        Group newGroup = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .course(course)
                .openDate(request.getOpenDate())
                .imageUrl(imageUrl)
                .build();

        groupRepository.save(newGroup);

        return ApiResponse.success("Guruh yaratildi");
    }

    @Override
    public ApiResponse<String> closeGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Guruh topilmadi"));

        groupRepository.delete(group);
        return ApiResponse.success("Guruh yopildi");
    }

    @Override
    public ApiResponse<List<GroupResponse>> getAll() {
        List<Group> groups = groupRepository.findAll();

        return ApiResponse.success(mapper.toResponseList(groups));
    }

    @Override
    public ApiResponse<GroupResponse> getById(UUID id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guruh topilmadi"));

        return ApiResponse.success(mapper.toResponse(group));
    }
}
