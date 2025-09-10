package com.example.bankcards.dto;

import com.example.bankcards.base.BaseDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDto extends BaseDto {
    @NotNull(message = "Source card ID is required")
    private Long fromCardId;

    @NotNull(message = "Destination card ID is required")
    private Long toCardId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    private BigDecimal amount;
}
