package com.example.bankcards.service.impl;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.TransactionDto;
import com.example.bankcards.dto.TransactionResponseDto;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.TransactionEntity;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.enums.TransactionStatus;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransactionRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.SessionUser;
import com.example.bankcards.service.TransactionService;
import com.example.bankcards.util.ResMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public ApiResponse<?> pay(TransactionDto transactionDto) {
        try{
            String username = SessionUser.getCurrentUser().orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            AuthUser authUser = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            CardEntity fromCardEntity = cardRepository.findById(transactionDto.getFromCardId()).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
            CardEntity tocardEntity = cardRepository.findById(transactionDto.getToCardId()).orElseThrow(() -> new ResourceNotFoundException("Card not found"));

            if (!fromCardEntity.getOwner().getId().equals(authUser.getId())) {
                return new ApiResponse<>(401, "You are not authorized to perform this action");
            }
            if (!(fromCardEntity.getStatus().equals(CardStatus.ACTIVE) && tocardEntity.getStatus().equals(CardStatus.ACTIVE))) {
                return new ApiResponse<>(402, "Cards not active");
            }
            if (fromCardEntity.getAmount().compareTo(transactionDto.getAmount()) < 0) {
                return new ApiResponse<>(402, "Cards not enough");
            }
            if (fromCardEntity.getId().equals(tocardEntity.getId())) {
                return new ApiResponse<>(402, "Cannot transfer to the same card");
            }


            fromCardEntity.setAmount(fromCardEntity.getAmount().subtract(transactionDto.getAmount()));
            tocardEntity.setAmount(tocardEntity.getAmount().add(transactionDto.getAmount()));

            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setAmount(transactionDto.getAmount());
            transactionEntity.setFromCard(fromCardEntity);
            transactionEntity.setToCard(tocardEntity);
            transactionEntity.setStatus(TransactionStatus.SUCCESS);

            cardRepository.save(fromCardEntity);
            cardRepository.save(tocardEntity);
            transactionRepository.save(transactionEntity);
            return new ApiResponse<>(200, "Payment successful");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getMyTransactions() {
        try{
            String username = SessionUser.getCurrentUser().orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            AuthUser authUser = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            List<TransactionEntity> allByFromCardOwnerId = transactionRepository.findAllByFromCard_Owner_Id(authUser.getId());
            List<TransactionResponseDto> list = allByFromCardOwnerId.stream().map(entity -> TransactionResponseDto.toDto(new TransactionResponseDto(), entity)).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getAllTransactionsAdmin(Pageable pageable) {
        try{
            Page<TransactionEntity> all = transactionRepository.findAll(pageable);
            List<TransactionResponseDto> list = all.getContent().stream().map(entity -> TransactionResponseDto.toDto(new TransactionResponseDto(), entity)).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getAllTransactionsBetween(Pageable pageable, LocalDateTime from, LocalDateTime to) {
        try{
            Page<TransactionEntity> allByDateBetween = transactionRepository.findAllByDateBetween(from, to, pageable);
            List<TransactionResponseDto> list = allByDateBetween.stream().map(entity -> TransactionResponseDto.toDto(new TransactionResponseDto(), entity)).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }
}
