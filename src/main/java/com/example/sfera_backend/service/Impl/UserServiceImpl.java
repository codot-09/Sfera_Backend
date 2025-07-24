package com.example.sfera_backend.service.Impl;

import com.example.sfera_backend.dto.AdminDTO;
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

    @Override
    public String saveAdmin(Long chatId) {
        if(userRepository.existsByChatId(chatId)){
            return "Admin mavjud";
        }

        User admin = User.builder()
                .chatId(chatId)
                .passwordHash(encoder.encode("admin123"))
                .build();

        userRepository.save(admin);

        return "Admin muvaffaqiyatli yaratildi";
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        List<User> admins = userRepository.findAll();
        return mapper.toDTOList(admins);
    }

    @Override
    public boolean isVerified(Long chatId) {
        User admin = userRepository.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin topilmadi"));

        return admin.isVerified();
    }

    @Override
    public String verifyAdmin(Long chatId,String phone,String fullName) {
        User admin = userRepository.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin topilmadi"));

        admin.setVerified(true);
        admin.setPhone(phone);
        admin.setFullName(fullName);
        userRepository.save(admin);

        return "Hisob tasdiqlandi";
    }

    @Override
    public void removeAdmin(Long chatId) {
        User admin = userRepository.findByChatId(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin topilmadi"));

        userRepository.delete(admin);
    }
}
