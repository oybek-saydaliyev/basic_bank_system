package com.example.bankcards.service;


import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.TransactionDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TransactionService {

    ApiResponse<?> pay(TransactionDto transactionDto);
    ApiResponse<?> getMyTransactions();

    ApiResponse<?> getAllTransactionsAdmin(Pageable pageable);
    ApiResponse<?> getAllTransactionsBetween(Pageable pageable, LocalDateTime from, LocalDateTime to);

}
