package com.example.sfera_backend.dto.response;

public record TokenResponse (
        String accessToken,
        String refreshToken,
        String tokenType
){
}
