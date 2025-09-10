package com.example.bankcards.controller;

import com.example.bankcards.base.ApiResponse;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.service.CardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    //admins
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCard(@RequestBody CardDto cardDto) {
        return ApiResponse.controller(cardService.create(cardDto));
    }

    @GetMapping("/admin/getOne/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCardById(@PathVariable("id") Long id) {
        return ApiResponse.controller(cardService.getOne(id));
    }

    @GetMapping("/admin/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCards(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.controller(cardService.getAllAdmin(PageRequest.of(page, size)));
    }

    @GetMapping("/admin/getAllByFilter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCardsByFilter(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String cardNumber) {
        return ApiResponse.controller(cardService.getCardsByFilterAdmin(cardNumber, PageRequest.of(page, size)));
    }

    @GetMapping("/admin/getAllByUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCardsByUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10")int size,
                                               @RequestParam(required = false) Long userId) {
        return ApiResponse.controller(cardService.getCardsByUserId(userId, PageRequest.of(page, size)));
    }

    @PatchMapping("/admin/update/status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateState(@PathVariable("id") Long id, @RequestParam CardStatus status) {
        return ApiResponse.controller(cardService.updateStatus(id, status));
    }

    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCard(@PathVariable("id") Long id, @RequestBody CardDto cardDto) {
        return ApiResponse.controller(cardService.updateCard(id, cardDto));
    }

    //users

    @GetMapping("/my")
    public ResponseEntity<?> getMyCards() {
        return ApiResponse.controller(cardService.getMyCards());
    }


}
