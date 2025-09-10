package com.example.bankcards.repository;

import com.example.bankcards.base.BaseRepository;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends BaseRepository<CardEntity> {
    List<CardEntity> findByOwner(AuthUser owner);

    List<CardEntity> findByOwner_Id(Long ownerId);

    Page<CardEntity> findAllByCardNumberStartsWith(String cardNumber, Pageable pageable);

    Page<CardEntity> findAllByOwner_Id(Long ownerId, Pageable pageable);

    Optional<CardEntity> findByCardNumber(String cardNumber);

    @Modifying
    @Query("update card c set c.status = 'EXPIRED' where c.expiryDate < :now")
    void updateExpiredCards(@Param("now") LocalDate now);


    List<CardEntity> findAllByExpiryDateBefore(LocalDate expiryDateBefore);
}