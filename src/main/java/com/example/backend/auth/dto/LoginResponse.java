package com.example.backend.auth.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        String username,
        String nickname
) {
}
