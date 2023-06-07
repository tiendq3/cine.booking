package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.SeatDTO;
import com.tiendq.cinebooking.model.entities.Seat;
import com.tiendq.cinebooking.repository.SeatRepository;
import com.tiendq.cinebooking.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

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
    public SeatDTO getSeatById(Long id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) throw new NotFoundException("Not found seat by id: " + id);
        return modelMapper.map(seat, SeatDTO.class);
    }

    @Override
    public void insertSeat(SeatDTO seatDTO) {
        Seat seat = modelMapper.map(seatDTO, Seat.class);
        seatRepository.save(seat);
    }

    @Override
    public void updateSeat(Long id, SeatDTO seatDTO) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) throw new NotFoundException("Not found seat by id: " + id);
        seat.setName(seatDTO.getName());
        seat.setType(seatDTO.getType());
        seat.setStatus(seatDTO.getStatus());
    }

    @Override
    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) throw new NotFoundException("Not found seat by id: " + id);
        seatRepository.delete(seat);
    }
}
