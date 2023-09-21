package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.exception.websocket.ErrorResponseWebsocket;
import com.tiendq.cinebooking.model.dtos.TicketDTO;
import com.tiendq.cinebooking.model.request.BookingRequest;
import com.tiendq.cinebooking.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class BookingController {

    private final BookingService bookingService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room.bookingTicket")
    @SendTo("/topic/publicBookingRoom")
    public BookingRequest bookingTicket(SimpMessageHeaderAccessor headerAccessor, @Payload BookingRequest bookingRequest) {
        log.warn("receive seatName: " + Arrays.toString(bookingRequest.getSeatNames()) + ", showtimeId: " + bookingRequest.getShowtimeId());
        log.warn("headerAccessor: " + headerAccessor);

        String token = headerAccessor.getFirstNativeHeader("Authorization");
        assert token != null;
        try {
            bookingService.bookingTicket(bookingRequest.getSeatNames(), bookingRequest.getShowtimeId(), token);
        } catch (Exception e) {
            simpMessagingTemplate.convertAndSend("/topic/error", new ErrorResponseWebsocket(e.getMessage()));
            return null;
        }
        return bookingRequest;
    }

    @GetMapping("/tickets/showtime/{showtimeId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByShowtimeId(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(bookingService.getTicketsByShowtimeId(showtimeId));
    }

    @GetMapping("/tickets/user-current")
    public ResponseEntity<List<TicketDTO>> getTicketsByCurrentUser() {
        return ResponseEntity.ok(bookingService.getTicketsByCurrentUser());
    }

    @GetMapping("/management/tickets")
    public ResponseEntity<Page<TicketDTO>> getPagingTicket(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookingService.getPagingTicket(page, size));
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(bookingService.getAllTickets());
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getTicketById(id));
    }

    @DeleteMapping("/tickets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        bookingService.deleteTicket(id);
    }
}
