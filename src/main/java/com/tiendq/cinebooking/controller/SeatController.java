package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.SeatDTO;
import com.tiendq.cinebooking.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/seats/{id}")
    public ResponseEntity<SeatDTO> getSeatById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET SEAT BY ID: " + id);
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @GetMapping("/seats")
    public ResponseEntity<List<SeatDTO>> getAllSeat() {
        log.warn("[CONTROLLER] - GET ALL SEAT");
        return ResponseEntity.ok(seatService.getAllSeat());
    }

    @GetMapping("/paging-seats")
    public ResponseEntity<Page<SeatDTO>> getPagingSeats(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(value = "roomFilter", defaultValue = "all") String roomFilter) {
        log.warn("[CONTROLLER] - GET ALL SEAT");
        return ResponseEntity.ok(seatService.getPagingSeats(page, size, roomFilter));
    }

    @PostMapping("/management/seats")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertSeat(@RequestBody SeatDTO seatDTO) {
        log.warn("[CONTROLLER] - INSERT NEW SEAT: " + seatDTO);
        seatService.insertSeat(seatDTO);
    }

    @PatchMapping("/management/seats/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSeat(@PathVariable Long id, @RequestBody SeatDTO seatDTO) {
        log.warn("[CONTROLLER] - UPDATE SEAT: " + seatDTO);
        seatService.updateSeat(id, seatDTO);
    }

    @DeleteMapping("/management/seats/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeat(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE SEAT BY ID:" + id);
        seatService.deleteSeat(id);
    }
}
