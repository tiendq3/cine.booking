package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.BadRequestException;
import com.tiendq.cinebooking.exception.DuplicateException;
import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.RoomDTO;
import com.tiendq.cinebooking.model.entities.Room;
import com.tiendq.cinebooking.model.enums.EStatusRoom;
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
        log.warn(roomDTO.getStatus());
        try{
            room.setStatus(EStatusRoom.valueOf(roomDTO.getStatus().toUpperCase()));
        }catch (Exception e){
            throw new BadRequestException("chọn room status khác ");
        }
        Room roomDB = roomRepository.findRoomByName(room.getName()).orElse(null);
        if(roomDB!=null) throw new DuplicateException("Tên phòng tồn tại");
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) throw new NotFoundException("Not found room by id: " + id);
        room.setName(roomDTO.getName());
        room.setCapacity(room.getCapacity());
        room.setDescription(roomDTO.getDescription());
        try{
            room.setStatus(EStatusRoom.valueOf(roomDTO.getStatus().toUpperCase()));
        }catch (Exception e){
            throw new BadRequestException("chọn room status khác ");
        }
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) throw new NotFoundException("Not found room by id: " + id);
        roomRepository.delete(room);
    }
}
