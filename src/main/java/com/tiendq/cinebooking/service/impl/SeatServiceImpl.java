package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.SeatDTO;
import com.tiendq.cinebooking.model.entities.Room;
import com.tiendq.cinebooking.model.entities.Seat;
import com.tiendq.cinebooking.model.enums.EStatusSeat;
import com.tiendq.cinebooking.model.enums.ETypeSeat;
import com.tiendq.cinebooking.repository.RoomRepository;
import com.tiendq.cinebooking.repository.SeatRepository;
import com.tiendq.cinebooking.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<SeatDTO> getAllSeat() {
        return seatRepository
                .findAll()
                .stream()
                .map(s -> modelMapper.map(s, SeatDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SeatDTO> getPagingSeats(int page, int size, String roomFilter) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Seat> seatPage = null;
        if (roomFilter.equals("all")) {
            seatPage = seatRepository.findAll(pageRequest);
            return seatPage.map(SeatDTO::new);
        } else {
            seatPage = seatRepository.findSeatsByRoomName(roomFilter, pageRequest);
        }
        return seatPage.map(SeatDTO::new);
    }

    @Override
    public SeatDTO getSeatById(Long id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) throw new NotFoundException("Not found seat by id: " + id);
        return modelMapper.map(seat, SeatDTO.class);
    }

    @Override
    public void insertSeat(SeatDTO seatDTO) {
        Seat seat = modelMapper.map(seatDTO, Seat.class);
        Room room = roomRepository.findRoomByName(seatDTO.getRoomName()).orElse(null);
        if (room == null) throw new NotFoundException("not exist room: " + seatDTO.getRoomName());
        seat.setRoom(room);
        seatRepository.save(seat);
    }

    @Override
    public void updateSeat(Long id, SeatDTO seatDTO) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) throw new NotFoundException("Not found seat by id: " + id);
        seat.setName(seatDTO.getName());
        seat.setType(ETypeSeat.valueOf(seatDTO.getType()));
        seat.setStatus(EStatusSeat.valueOf(seatDTO.getStatus().toUpperCase()));
        seatRepository.save(seat);
    }

    @Override
    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) throw new NotFoundException("Not found seat by id: " + id);
        seatRepository.delete(seat);
    }
}
