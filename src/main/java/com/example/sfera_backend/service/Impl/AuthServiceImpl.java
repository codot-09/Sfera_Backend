package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.bot.BotCode;
import com.example.sfera_backend.dto.request.LoginRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.TokenResponse;
import com.example.sfera_backend.entity.User;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.jwt.JwtProvider;
import com.example.sfera_backend.repository.UserRepository;
import com.example.sfera_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final BotCode botService;

    @Override
    public ApiResponse<TokenResponse> login(LoginRequest request) {
        User user = userRepository.findByPhone(request.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));

        if(!encoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new ResourceNotFoundException("Ma'lumotlar noto'g'ri");
        }

        String accessToken = jwtProvider.generateToken(user.getId(), user.getPhone());
        String refreshToken = jwtProvider.generateRefreshToken(user.getPhone());

        TokenResponse response = new TokenResponse(accessToken,refreshToken,"Bearer");

        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<String> refreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("Noto'g'ri token"));

        String accessToken = jwtProvider.generateToken(user.getId(), user.getPhone());

        return ApiResponse.success(accessToken);
    }

    @Override
    public ApiResponse<String> logout(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("Noto'g'ri token"));

        user.setRefreshToken(null);
        userRepository.save(user);

        return ApiResponse.success("Tizimdan chiqildi");
    }
}
