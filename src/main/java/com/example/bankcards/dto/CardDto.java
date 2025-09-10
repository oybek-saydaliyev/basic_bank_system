package com.example.bankcards.dto;

import com.example.bankcards.base.BaseDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.enums.CardStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CardDto extends BaseDto {
    @NotBlank(message = "Card number cannot be blank")
    @Pattern(regexp = "\\d{16}", message = "Card number must contain exactly 16 digits")
    private String cardNumber;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    @NotNull(message = "Card status is required")
    private CardStatus status;

    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
    private BigDecimal amount;

    @NotNull(message = "Owner cannot be null")
    private Long ownerId;

    public static CardEntity toEntity(CardEntity entity, CardDto cardDto) {
        entity.setCardNumber(cardDto.getCardNumber() != null ? cardDto.getCardNumber() : entity.getCardNumber());
        entity.setExpiryDate(cardDto.getExpiryDate() != null ? cardDto.getExpiryDate() : entity.getExpiryDate());
        entity.setStatus(cardDto.getStatus() != null ? cardDto.getStatus() : entity.getStatus());
        entity.setAmount(cardDto.getAmount() != null ? cardDto.getAmount() : entity.getAmount());
        return entity;
    }

    public static CardDto toDto(CardEntity entity, CardDto dto) {
        dto.setCardNumber(entity.getMaskedCardNumber()); //using masked
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setStatus(entity.getStatus());
        dto.setAmount(entity.getAmount());
        return dto;
    }
}
