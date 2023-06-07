package com.tiendq.cinebooking;

import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.model.enums.ERole;
import com.tiendq.cinebooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class CineBookingApplication implements CommandLineRunner {
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(CineBookingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = User
                .builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .roles(Set.of(ERole.ADMIN))
                .build();
        User user = User
                .builder()
                .email("user@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .roles(Set.of(ERole.USER))
                .build();
        User admin1 = userRepository.findUserByEmail("admin@gmail.com");
        User user1 = userRepository.findUserByEmail("user@gmail.com");
        if (admin1 == null) userRepository.save(admin);
        if (user1 == null) userRepository.save(user);
    }
}
