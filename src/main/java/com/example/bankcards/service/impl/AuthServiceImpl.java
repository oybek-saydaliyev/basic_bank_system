package com.example.bankcards.service.impl;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.LoginRequestDto;
import com.example.bankcards.dto.LoginResponseDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtUtil;
import com.example.bankcards.security.SessionUser;
import com.example.bankcards.service.AuthService;
import com.example.bankcards.util.ResMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse<?> login(LoginRequestDto loginRequestDto) {
        try {
            Optional<AuthUser> userOptional = userRepository.findByUsername(loginRequestDto.getUsername());

            if (userOptional.isEmpty()) {
                return new ApiResponse<>(ResMessages.USER_NOT_FOUND, 404);
            }

            AuthUser user = userOptional.get();

            if (!user.getIsActive()) {
                return new ApiResponse<>(ResMessages.USER_NOT_ACTIVE, 403);
            }

            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                return new ApiResponse<>(ResMessages.INVALID_PASSWORD, 401);
            }

            String accessToken = jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

            LoginResponseDto response = LoginResponseDto.builder()
                    .user(UserResponseDto.toDto(user, new UserResponseDto()))
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(jwtUtil.getExpirationTime())
                    .tokenType("Bearer")
                    .build();

            log.info("User {} logged in successfully", user.getUsername());
            return new ApiResponse<>(200, ResMessages.SUCCESS, response);

        } catch (Exception e) {
            log.error("Login error: ", e);
            return new ApiResponse<>(409, ResMessages.SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<?> refreshToken(String refreshToken) {
        try {

            if (jwtUtil.isRefreshTokenExpired(refreshToken)) {
                return new ApiResponse<>(ResMessages.EXPIRED_TOKEN, 401);
            }

            String username = jwtUtil.getUsernameFromRefreshToken(refreshToken);
            Optional<AuthUser> userOptional = userRepository.findByUsername(username);

            if (userOptional.isEmpty()) {
                return new ApiResponse<>(ResMessages.USER_NOT_FOUND, 404);
            }

            AuthUser user = userOptional.get();

            if (!user.getIsActive()) {
                return new ApiResponse<>(ResMessages.USER_NOT_ACTIVE, 403);
            }

            String newAccessToken = jwtUtil.generateAccessToken(user.getUsername());

            LoginResponseDto response = LoginResponseDto.builder()
                    .user(UserResponseDto.toDto(user, new UserResponseDto()))
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(jwtUtil.getExpirationTime())
                    .tokenType("Bearer")
                    .build();

            return new ApiResponse<>(200, ResMessages.SUCCESS, response);

        } catch (Exception e) {
            log.error("Token refresh error: ", e);
            return new ApiResponse<>(ResMessages.EXPIRED_TOKEN, 409);
        }
    }

    @Override
    public ApiResponse<?> getMe() {
        try{
            Optional<String> currentUser = SessionUser.getCurrentUser();
            if (currentUser.isEmpty()) {
                return new ApiResponse<>(404, ResMessages.USER_NOT_FOUND);
            }

            String username = currentUser.get();
            Optional<AuthUser> byUsername = userRepository.findByUsername(username);
            if (byUsername.isEmpty()) {
                return new ApiResponse<>(404, ResMessages.USER_NOT_FOUND);
            }

            AuthUser authUser = byUsername.get();
            UserResponseDto response = UserResponseDto.toDto(authUser, new UserResponseDto());
            return new ApiResponse<>(200, ResMessages.SUCCESS, response);
        }catch (Exception e){
            log.error("get me error: ", e);
            return new ApiResponse<>(ResMessages.SERVER_ERROR, 409);
        }
    }
}
