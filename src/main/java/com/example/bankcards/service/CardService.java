package com.example.bankcards.service;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.enums.CardStatus;
import org.springframework.data.domain.Pageable;

public interface CardService {
    ApiResponse<?> create(CardDto dto);
    ApiResponse<?> getOne(Long id);
    ApiResponse<?> getAllAdmin(Pageable pageable);
    ApiResponse<?> getCardsByFilterAdmin(String cardNumber, Pageable pageable);
    ApiResponse<?> getCardsByUserId(Long userId, Pageable pageable);
    ApiResponse<?> getMyCards();
    ApiResponse<?> updateStatus(Long id, CardStatus status);
    ApiResponse<?> updateCard(Long id, CardDto dto);
}
