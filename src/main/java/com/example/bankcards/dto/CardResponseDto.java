package com.example.bankcards.dto;

import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CardResponseDto {
    private Long id;
    private String cardNumber;
    private LocalDate expiryDate;
    private CardStatus status;
    private BigDecimal amount;
    private UserResponseDto user;

    public static CardResponseDto createCardResponseDto(CardEntity entity, CardResponseDto dto) {
        dto.setId(entity.getId());
        dto.setCardNumber(entity.getCardNumber());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setStatus(entity.getStatus());
        dto.setAmount(entity.getAmount());
        dto.setUser(UserResponseDto.toDto(entity.getOwner(), new UserResponseDto()));
        return dto;
    }
}
