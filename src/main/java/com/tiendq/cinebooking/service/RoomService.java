package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    List<RoomDTO> getAllRoom();

    RoomDTO getRoomById(Long id);

    void insertRoom(RoomDTO roomDTO);

    void updateRoom(Long id, RoomDTO roomDTO);

    void deleteRoom(Long id);
}
