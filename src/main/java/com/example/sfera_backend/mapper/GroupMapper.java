package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.response.GroupResponse;
import com.example.sfera_backend.entity.Group;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GroupMapper {
    public GroupResponse toResponse(Group group){
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getOpenDate(),
                group.getCourse().getName(),
                group.getImageUrl(),
                0,
                group.getCourse().getTeacher().getFullName()
        );
    }

    public List<GroupResponse> toResponseList(List<Group> groups){
        return groups.stream().map(this::toResponse).toList();
    }
}
