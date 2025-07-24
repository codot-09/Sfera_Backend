package com.example.sfera_backend.controller;

import com.example.sfera_backend.dto.response.ApiResponse;
import com.example.sfera_backend.dto.response.UserDetailsResponse;
import com.example.sfera_backend.entity.User;
import com.example.sfera_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(
            summary = "O'z profilini ko'rish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getProfile(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(userService.getProfile(user));
    }

    @GetMapping
    @Operation(
            summary = "Barcha foydalanuvchilarni ko'rish",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserDetailsResponse>>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PatchMapping
    @Operation(
            summary = "Ismni yangilash",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateName(
            @RequestParam String name,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(userService.updateName(name,user));
    }
}
