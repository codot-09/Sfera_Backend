package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.request.LoginRequest;
import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.TokenResponse;
import com.example.sfera_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Tizimga kirish"
    )
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    @Operation(
            summary = "Token yangilash"
    )
    public ResponseEntity<ApiResponse<String>> refreshToken(
            @RequestBody String refreshToken
    ){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @PatchMapping("/forgot-password")
    @Operation(
            summary = "Parolni yangilash"
    )
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @RequestParam String phoneNumber
    ){
        return ResponseEntity.ok(authService.forgotPassword(phoneNumber));
    }

    @PostMapping("/log-out")
    @Operation(
            summary = "Tizimdan chiqish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestBody String refreshToken
    ){
        return ResponseEntity.ok(authService.logout(refreshToken));
    }
}
