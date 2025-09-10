package com.example.bankcards.dto;

import com.example.bankcards.base.BaseDto;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.entity.enums.AuthRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto extends BaseDto {
    private String username;
    private String email;
    private String fullName;
    private String password;
    private Boolean isActive = true;
    private AuthRole role;

    public static AuthUser toEntity(UserCreateDto dto, AuthUser entity) {
        entity.setUsername(dto.getUsername() != null ? dto.getUsername() : entity.getUsername());
        entity.setEmail(dto.getEmail() != null ? dto.getEmail() : entity.getEmail());
        entity.setFullName(dto.getFullName() != null ? dto.getFullName() : entity.getFullName());
        entity.setPassword(dto.getPassword() != null ? dto.getPassword() : entity.getPassword());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : entity.getIsActive());
        entity.setRole(dto.getRole() != null ? dto.getRole() : entity.getRole());
        return entity;
    }
}
