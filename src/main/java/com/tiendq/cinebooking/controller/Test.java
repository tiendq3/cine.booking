package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.entities.Room;
import com.tiendq.cinebooking.model.entities.Seat;
import com.tiendq.cinebooking.model.enums.EStatusSeat;
import com.tiendq.cinebooking.repository.RoomRepository;
import com.tiendq.cinebooking.repository.SeatRepository;
import com.tiendq.cinebooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500/seats.html")
@RestController
public class Test {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @GetMapping(value = "/home")
    public String getChart() {
        return "chart";
    }

//    @GetMapping("/rooms/{roomId}")
//    public ResponseEntity<List<Seat>> getSeatsByRoomId(@PathVariable Long roomId) {
//        List<Seat> seatList = seatRepository.findSeatByRoomId(roomId, Sort.by(Sort.Direction.ASC, "id"));
//        return ResponseEntity.ok(seatList);
//    }

//    @PatchMapping("/seats/{seatId}")
//    public void bookingSeat(@PathVariable Long seatId) {
//        Seat seat = seatRepository.findById(seatId).orElse(null);
//        assert seat != null;
//        seat.setStatus(EStatusSeat.booked);
//        seatRepository.save(seat);
//    }

    @GetMapping("/rooms/all")
    public ResponseEntity<List<Room>> getAllRoom() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @MessageMapping("/room.bookingSeat")
    @SendTo("/topic/publicRoomSeats")
    public Seat bookingSeat(@Payload Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElse(null);
        assert seat != null;
        seat.setStatus(EStatusSeat.ACTIVE);
        return seatRepository.save(seat);
    }

//    @MessageMapping("/room.addUser")
//    @SendTo("/topic/publicRoomSeats")
//    public void addUser(@Payload String token, SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", token);
//    }
}
