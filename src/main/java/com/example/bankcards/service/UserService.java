package com.example.bankcards.service;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.UserCreateDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    ApiResponse<?> createUser(UserCreateDto dto);
    ApiResponse<?> getOne(Long id);
    ApiResponse<?> getAll(Pageable pageable);
    ApiResponse<?> updateActive(Long id, Boolean active);
    ApiResponse<?> delete(Long id);
}
