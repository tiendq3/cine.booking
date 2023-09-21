package com.tiendq.cinebooking.repository;

import com.tiendq.cinebooking.model.entities.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    List<OTP> findByEmail(String email);
}
