package com.example.bankcards.repository;

import com.example.bankcards.base.BaseRepository;
import com.example.bankcards.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends BaseRepository<TransactionEntity> {
    List<TransactionEntity> findAllByFromCard_Owner_Id(Long fromCardOwnerId);

    Page<TransactionEntity> findAllByDateBetween(LocalDateTime dateAfter, LocalDateTime dateBefore, Pageable pageable);
}