package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.response.UserDetailsResponse;
import com.example.sfera_backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDetailsResponse toResponse(User user){
        return new UserDetailsResponse(
                user.getFullName(),
                user.getPhone()
        );
    }

    public List<UserDetailsResponse> toResponseList(List<User> users){
        return users.stream().map(this::toResponse).toList();
    }
}
