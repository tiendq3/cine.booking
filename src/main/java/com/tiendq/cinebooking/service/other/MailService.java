package com.tiendq.cinebooking.service.other;

import com.tiendq.cinebooking.exception.EmailExistException;
import com.tiendq.cinebooking.model.dtos.MailMessage;
import com.tiendq.cinebooking.model.entities.OTP;
import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.model.request.Login;
import com.tiendq.cinebooking.repository.OTPRepository;
import com.tiendq.cinebooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;

    private final OTPRepository otpRepository;

    private final UserRepository userRepository;

    public void mailSender(MailMessage mailMessage) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailMessage.getEmail());
        message.setText(mailMessage.getContent());
        message.setSubject(mailMessage.getTitle());
        javaMailSender.send(message);
    }

    public void sendOTP(Login login) throws MailException {
        User user = userRepository.findUserByEmail(login.getEmail());
        if (user != null) throw new EmailExistException(login.getEmail() + " exist!");
        String otpCode = (int) (10000 * Math.random()) + "";
        OTP otp = OTP
                .builder()
                .email(login.getEmail())
                .otp(otpCode)
                .createAt(Instant.now())
                .build();
        otpRepository.save(otp);
        MailMessage mailMessage = MailMessage
                .builder()
                .email(login.getEmail())
                .title("Email verification")
                .content("Your OTP: " + otp.getOtp())
                .build();
        mailSender(mailMessage);
    }
}
