package com.example.bankcards.dto;

import com.example.bankcards.entity.TransactionEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponseDto {
    private Long id;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private String toAccountOwner;
    private String toAccountOwnerCardNumber;
    private String fromAccountOwner;
    private String fromAccountOwnerCardNumber;


    public static TransactionResponseDto toDto(TransactionResponseDto dto, TransactionEntity entity){
        dto.setId(entity.getId());
        dto.setTransactionDate(entity.getDate());
        dto.setAmount(entity.getAmount());
        dto.setToAccountOwner(entity.getToCard().getOwner().getFullName());
        dto.setToAccountOwnerCardNumber(entity.getToCard().getMaskedCardNumber()); //using masked
        dto.setFromAccountOwner(entity.getFromCard().getOwner().getFullName());
        dto.setFromAccountOwnerCardNumber(entity.getFromCard().getMaskedCardNumber());
        return dto;
    }

}
