package com.tiendq.cinebooking.config;

import com.tiendq.cinebooking.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class SchedulingConfig {

    private final BookingService bookingService;

    @Scheduled(fixedRate = 60000) // Chạy sau mỗi 60 giây
    public void cancelExpiredTickets() {
        log.warn("check ticket cancellation time");
        bookingService.cancelExpiredTickets();
    }
}
