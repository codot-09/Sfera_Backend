package com.example.sfera_backend.mapper;

import com.example.sfera_backend.dto.AdminDTO;
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

    public AdminDTO toDTP(User admin){
        return new AdminDTO(
                admin.getChatId(),
                admin.getFullName(),
                admin.getPhone()
        );
    }

    public List<AdminDTO> toDTOList(List<User> admins){
        return admins.stream().map(this::toDTP).toList();
    }
}
