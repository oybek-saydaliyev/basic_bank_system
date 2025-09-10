package com.example.bankcards.controller;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.LoginRequestDto;
import com.example.bankcards.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
        return ApiResponse.controller(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken){
        return ApiResponse.controller(authService.refreshToken(refreshToken));
    }

    @GetMapping("/getMe")
    public ResponseEntity<?> getMe(){
        return ApiResponse.controller(authService.getMe());
    }
}
