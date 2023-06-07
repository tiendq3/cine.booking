package com.tiendq.cinebooking.repository;

import com.tiendq.cinebooking.model.entities.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    OTP findByEmail(String email);
}
