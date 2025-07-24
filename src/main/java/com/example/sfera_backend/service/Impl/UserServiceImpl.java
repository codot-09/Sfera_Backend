package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.UserDetailsResponse;
import com.example.sfera_backend.entity.User;
import com.example.sfera_backend.exception.ResourceNotFoundException;
import com.example.sfera_backend.mapper.UserMapper;
import com.example.sfera_backend.repository.UserRepository;
import com.example.sfera_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public String updatePassword(Long chatId, String password) {
        User user = userRepository.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));

        user.setPasswordHash(encoder.encode(password));
        userRepository.save(user);

        return "Parol muvaffaqiyatli yangilandi";
    }

    @Override
    public ApiResponse<UserDetailsResponse> getProfile(User user) {
        return ApiResponse.success(mapper.toResponse(user));
    }

    @Override
    public ApiResponse<List<UserDetailsResponse>> getAll() {
        List<User> users = userRepository.findAll();
        return ApiResponse.success(mapper.toResponseList(users));
    }

    @Override
    public ApiResponse<String> updateName(String name, User user) {
        user.setFullName(name);
        userRepository.save(user);

        return ApiResponse.success("Ism muvaffaqiyatli yangilandi");
    }
}
