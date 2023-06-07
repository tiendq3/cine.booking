package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.RoomDTO;
import com.tiendq.cinebooking.model.entities.Room;
import com.tiendq.cinebooking.repository.RoomRepository;
import com.tiendq.cinebooking.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<RoomDTO> getAllRoom() {
        return roomRepository
                .findAll()
                .stream()
                .map(r -> modelMapper.map(r, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) throw new NotFoundException("Not found room by id: " + id);
        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    public void insertRoom(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) throw new NotFoundException("Not found room by id: " + id);
        room.setName(roomDTO.getName());
        room.setCapacity(room.getCapacity());
        room.setStatus(roomDTO.getStatus());
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) throw new NotFoundException("Not found room by id: " + id);
        roomRepository.delete(room);
    }
}
