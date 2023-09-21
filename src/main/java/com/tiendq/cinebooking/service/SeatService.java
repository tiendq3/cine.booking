package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.SeatDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {
    List<SeatDTO> getAllSeat();

    Page<SeatDTO> getPagingSeats(int page, int size, String roomFilter);

    SeatDTO getSeatById(Long id);

    void insertSeat(SeatDTO seatDTO);

    void updateSeat(Long id, SeatDTO seatDTO);

    void deleteSeat(Long id);
}
