package com.example.bankcards.service.impl;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.UserCreateDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.ResMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<?> createUser(UserCreateDto dto) {
        try{
            AuthUser authUser = UserCreateDto.toEntity(dto, new AuthUser());
            authUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            AuthUser user = userRepository.save(authUser);
            return new ApiResponse<>(200, ResMessages.SUCCESS, UserResponseDto.toDto(user, new UserResponseDto()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getOne(Long id) {
        try{
            AuthUser authUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            return new ApiResponse<>(200, ResMessages.SUCCESS, UserResponseDto.toDto(authUser, new UserResponseDto()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getAll(Pageable pageable) {
        try{
            Page<AuthUser> all = userRepository.findAll(pageable);
            List<UserResponseDto> list = all.getContent().stream().map(user -> UserResponseDto.toDto(user, new UserResponseDto()))
                    .toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, new PageImpl<>(list, all.getPageable(), all.getTotalElements()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> updateActive(Long id, Boolean active) {
        try{
            AuthUser authUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            authUser.setIsActive(active);
            userRepository.save(authUser);
            return new ApiResponse<>(200, ResMessages.SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        try{
            AuthUser authUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            authUser.setDeleted(true);
            userRepository.save(authUser);
            return new ApiResponse<>(200, ResMessages.SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }
}
