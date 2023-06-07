package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.request.Login;
import com.tiendq.cinebooking.service.other.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send-otp")
    public void sendOTP(@RequestBody @Valid Login login) {
        mailService.sendOTP(login);
    }

    @GetMapping("/order-info")
    public void sendOrderInfo() {
    }
}
