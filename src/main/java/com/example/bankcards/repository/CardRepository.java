package com.example.bankcards.repository;

import com.example.bankcards.base.BaseRepository;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends BaseRepository<CardEntity> {
    List<CardEntity> findByOwner(AuthUser owner);

    List<CardEntity> findByOwner_Id(Long ownerId);

    Page<CardEntity> findAllByCardNumberStartsWith(String cardNumber, Pageable pageable);

    Page<CardEntity> findAllByOwner_Id(Long ownerId, Pageable pageable);

    Optional<CardEntity> findByCardNumber(String cardNumber);
}