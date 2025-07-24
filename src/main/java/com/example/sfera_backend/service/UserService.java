package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.AdminDTO;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.UserDetailsResponse;
import com.example.sfera_backend.entity.User;

import java.util.List;

public interface UserService {
    ApiResponse<UserDetailsResponse> getProfile(User user);
    ApiResponse<List<UserDetailsResponse>> getAll();
    ApiResponse<String> updateName(String name,User user);
    String saveAdmin(Long chatId);
    List<AdminDTO> getAllAdmins();
    boolean isVerified(Long chatId);
    String verifyAdmin(Long chatId,String phone,String fullName);
    void removeAdmin(Long chatId);
}
