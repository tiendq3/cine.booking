package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.SeatDTO;
import com.tiendq.cinebooking.model.entities.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {
    List<SeatDTO> getAllSeat();

    SeatDTO getSeatById(Long id);

    void insertSeat(SeatDTO seatDTO);

    void updateSeat(Long id, SeatDTO seatDTO);

    void deleteSeat(Long id);
}
