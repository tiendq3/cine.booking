package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.EmailExistException;
import com.tiendq.cinebooking.model.entities.OTP;
import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.model.enums.ERole;
import com.tiendq.cinebooking.model.request.Login;
import com.tiendq.cinebooking.model.request.Register;
import com.tiendq.cinebooking.repository.OTPRepository;
import com.tiendq.cinebooking.repository.UserRepository;
import com.tiendq.cinebooking.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OTPRepository otpRepository;

    @Transactional
    @Override
    public void register(Register register) {
        User user = userRepository.findUserByEmail(register.getEmail());
        if (user != null) throw new EmailExistException("Email already exists!");
        User newUser = User
                .builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .roles(Set.of(ERole.USER))
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
        OTP otp = otpRepository.findByEmail(register.getEmail());
        if (otp != null && otp.getOtp().equals(register.getOtp()) && (Instant.now().getEpochSecond() - otp.getCreateAt().getEpochSecond()) <= 120) {
            userRepository.save(newUser);
            otpRepository.delete(otp);
        } else throw new RuntimeException("otp is incorrect or more than 2 minutes");
        if (Instant.now().getEpochSecond() - otp.getCreateAt().getEpochSecond() > 120) {
            otpRepository.delete(otp);
        }
    }

    @Override
    public void login(Login login) {

    }

    @Override
    public void changePassword() {

    }
}
