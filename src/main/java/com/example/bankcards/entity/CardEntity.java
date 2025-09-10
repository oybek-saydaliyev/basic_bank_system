package com.example.bankcards.entity;

import com.example.bankcards.base.BaseEntity;
import com.example.bankcards.entity.enums.CardStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "card")
public class CardEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "\\d{16}", message = "Card number must contain exactly 16 digits")
    private String cardNumber;
    @Column(nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private AuthUser owner;


    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****"; // for security
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

}
