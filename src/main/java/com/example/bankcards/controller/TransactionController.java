package com.example.bankcards.controller;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.TransactionDto;
import com.example.bankcards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody TransactionDto transactionDto) {
        return ApiResponse.controller(transactionService.pay(transactionDto));
    }


    @GetMapping("/getMyTransactions")
    public ResponseEntity<?> getMyTransactions() {
        return ApiResponse.controller(transactionService.getMyTransactions());
    }

    //admins
    @GetMapping("/getAllTransactions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTransactions(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.controller(transactionService.getAllTransactionsAdmin(PageRequest.of(page, size)));
    }

    @GetMapping("/getAllTransactionsBetween")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTransactionsBetween(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam LocalDateTime from,
                                                       @RequestParam LocalDateTime to) {
        return ApiResponse.controller(transactionService.getAllTransactionsBetween(PageRequest.of(page, size), from, to));
    }
}
