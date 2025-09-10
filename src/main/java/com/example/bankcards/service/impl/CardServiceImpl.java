package com.example.bankcards.service.impl;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.entity.AuthUser;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.SessionUser;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.ResMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Override
    public ApiResponse<?> create(CardDto dto) {
        try{
            AuthUser authUser = userRepository.findById(dto.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            CardEntity cardEntity = CardDto.toEntity(new CardEntity(), dto);
            cardEntity.setOwner(authUser);
            CardEntity save = cardRepository.save(cardEntity);
            CardResponseDto cardResponseDto = CardResponseDto.createCardResponseDto(save, new CardResponseDto());
            return new ApiResponse<>(200, ResMessages.SUCCESS, cardResponseDto);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getOne(Long id) {
        try{
            CardEntity cardEntity = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
            CardResponseDto cardResponseDto = CardResponseDto.createCardResponseDto(cardEntity, new CardResponseDto());
            return new ApiResponse<>(200, ResMessages.SUCCESS, cardResponseDto);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getAllAdmin(Pageable pageable) {
        try{
            Page<CardEntity> all = cardRepository.findAll(pageable);
            List<CardResponseDto> list = all.getContent().stream().map(entity -> CardResponseDto.createCardResponseDto(entity, new CardResponseDto())).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, new PageImpl<>(list, pageable, all.getTotalElements()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getCardsByFilterAdmin(String cardNumber, Pageable pageable) {
        try{
            Page<CardEntity> allByCardNumberStartsWith = cardRepository.findAllByCardNumberStartsWith(cardNumber, pageable);
            List<CardResponseDto> list = allByCardNumberStartsWith.getContent().stream().map(entity -> CardResponseDto.createCardResponseDto(entity, new CardResponseDto())).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, new PageImpl<>(list, pageable, allByCardNumberStartsWith.getTotalElements()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getCardsByUserId(Long userId, Pageable pageable) {
        try{
            Page<CardEntity> allByOwnerId = cardRepository.findAllByOwner_Id(userId, pageable);
            List<CardResponseDto> list = allByOwnerId.getContent().stream().map(entity -> CardResponseDto.createCardResponseDto(entity, new CardResponseDto())).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, new PageImpl<>(list, pageable, allByOwnerId.getTotalElements()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> getMyCards() {
        try{
            String username = SessionUser.getCurrentUser().orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            AuthUser authUser = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(ResMessages.USER_NOT_FOUND));
            List<CardDto> list = cardRepository.findByOwner_Id(authUser.getId()).stream().map(
                    user -> CardDto.toDto(user, new CardDto())
            ).toList();
            return new ApiResponse<>(200, ResMessages.SUCCESS, list);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> updateStatus(Long id, CardStatus status) {
        try{
            CardEntity cardEntity = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
            cardEntity.setStatus(status);
            cardRepository.save(cardEntity);
            return new ApiResponse<>(200, ResMessages.SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> updateCard(Long id, CardDto dto) {
        try {
            CardEntity cardEntity = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
            CardEntity card = CardDto.toEntity(cardEntity, dto);
            return new ApiResponse<>(200, ResMessages.SUCCESS, cardRepository.save(card));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ApiResponse<>(409, e.getMessage());
        }
    }
}
