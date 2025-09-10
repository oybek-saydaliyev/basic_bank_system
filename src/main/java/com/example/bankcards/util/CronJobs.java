package com.example.bankcards.util;

import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.repository.CardRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class CronJobs {


    private final CardRepository cardRepository;

    public CronJobs(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateStatusCard() {
        cardRepository.updateExpiredCards(LocalDate.now());
    }
}
