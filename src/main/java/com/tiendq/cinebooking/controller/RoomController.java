package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.RoomDTO;
import com.tiendq.cinebooking.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET ROOM BY ID: " + id);
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRoom() {
        log.warn("[CONTROLLER] - GET ALL ROOM");
        return ResponseEntity.ok(roomService.getAllRoom());
    }

    @PostMapping("/management/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertRoom(@RequestBody @Valid RoomDTO roomDTO) {
        log.warn("[CONTROLLER] - INSERT NEW ROOM: " + roomDTO);
        roomService.insertRoom(roomDTO);
    }

    @PatchMapping("/management/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO) {
        log.warn("[CONTROLLER] - UPDATE ROOM: " + roomDTO + ",ROOMID: " + id);
        roomService.updateRoom(id, roomDTO);
    }

    @DeleteMapping("/management/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE ROOM BY ID:" + id);
        roomService.deleteRoom(id);
    }
}
