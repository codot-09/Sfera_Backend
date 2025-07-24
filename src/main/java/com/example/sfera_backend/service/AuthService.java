package com.example.sfera_backend.service;

import com.example.sfera_backend.dto.request.LoginRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.TokenResponse;

public interface AuthService {
    ApiResponse<TokenResponse> login(LoginRequest request);
    ApiResponse<String> refreshToken(String refreshToken);
    ApiResponse<String> logout(String refreshToken);
}
