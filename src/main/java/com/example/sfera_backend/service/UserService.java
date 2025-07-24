package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.UserDetailsResponse;
import com.example.sfera_backend.entity.User;

import java.util.List;

public interface UserService {
    String updatePassword(Long chatId,String password);
    ApiResponse<UserDetailsResponse> getProfile(User user);
    ApiResponse<List<UserDetailsResponse>> getAll();
    ApiResponse<String> updateName(String name,User user);
}
