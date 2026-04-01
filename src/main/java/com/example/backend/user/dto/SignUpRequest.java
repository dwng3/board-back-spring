package com.example.backend.user.dto;

public record SignUpRequest(
    String username,
    String password,
    String email,
    String nickname
) {
}
