package com.example.bankcards.dto;

import com.example.bankcards.base.BaseDto;
import com.example.bankcards.entity.AuthUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto extends BaseDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Boolean isActive;
    private Boolean deleted;

    public static UserResponseDto toDto(AuthUser entity, UserResponseDto dto) {
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        dto.setIsActive(entity.getIsActive());
        dto.setDeleted(entity.getDeleted());
        return dto;
    }
}
