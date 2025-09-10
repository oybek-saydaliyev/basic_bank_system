package com.example.bankcards.service;


import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.LoginRequestDto;

public interface AuthService {
    ApiResponse<?> login(LoginRequestDto loginRequestDto);
    ApiResponse<?> refreshToken(String token);
    ApiResponse<?> getMe();
}
