package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.exception.websocket.WebsocketException;
import com.tiendq.cinebooking.model.dtos.TicketDTO;
import com.tiendq.cinebooking.model.entities.Seat;
import com.tiendq.cinebooking.model.entities.Showtime;
import com.tiendq.cinebooking.model.entities.Ticket;
import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.model.enums.EStatusSeat;
import com.tiendq.cinebooking.model.enums.EStatusTicket;
import com.tiendq.cinebooking.model.enums.ETypeSeat;
import com.tiendq.cinebooking.model.request.BookingRequest;
import com.tiendq.cinebooking.repository.*;
import com.tiendq.cinebooking.security.jwt.JwtProvider;
import com.tiendq.cinebooking.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final SeatRepository seatRepository;

    private final FilmRepository filmRepository;

    private final ShowtimeRepository showtimeRepository;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final ModelMapper modelMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Page<TicketDTO> getPagingTicket(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository
                .findAllByShowtimeStartTimeEpochSecondGreaterThanNow(Instant.now(), pageable)
                .map(TicketDTO::new);
    }

    @Override
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = bookingRepository.findById(id).orElse(null);
        if (ticket == null) throw new NotFoundException("Not found ticket by id: " + id);
        return new TicketDTO(ticket);
    }


    @Override
    public List<TicketDTO> getTicketsByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;
        User user = userRepository.findUserByEmail(authentication.getName());
        List<Ticket> tickets = bookingRepository.findTicketsByUser(user);
        return tickets
                .stream()
                .map(TicketDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getTicketsByShowtimeId(Long showtimeId) {
        List<Ticket> tickets = bookingRepository.findTicketByShowtimeId(showtimeId, Sort.by(Sort.Direction.ASC, "seat"));
        return tickets
                .stream()
                .map(TicketDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getTicketsByRoomOfShowtime(Long showtimeId, Long roomId) {
        return null;
    }

    @Transactional
    @Override
    public BookingRequest bookingTicket(String[] seatNames, Long showtimeId, String token) {
        // khi admin tạo ra 1 showtime --> đi kèm với đó việc tạo ra các vé cho showtime
        Showtime showtime = showtimeRepository.findById(showtimeId).orElse(null);
        if (showtime == null) throw new WebsocketException("This showtime is not ready, please go to another showtime");

        if (token.equals("Bearer null")) throw new WebsocketException("You must be logged in to purchase tickets");
        String emailUser = jwtProvider.getUserNameFromJwtToken(token);
        User user = userRepository.findUserByEmail(emailUser);
        if (user == null) throw new WebsocketException("You must be logged in to purchase tickets");

        List<Ticket> tickets = Arrays
                .stream(seatNames)
                .map(s -> {
                    Ticket ticket = bookingRepository.findTicketBySeatNameAndShowtime(s, showtime);
                    if (ticket == null)
                        throw new WebsocketException("Seat " + s + " is faulty, please choose another seat");
                    return ticket;
                })
                .toList();
        if (tickets.size() == 0) throw new WebsocketException("choose the seat you want to book");

        Double price = showtime.getPrice();
        for (Ticket ticket : tickets) {
            Seat seat = ticket.getSeat();
            if (seat == null) throw new WebsocketException("This seat not exist");
            if (!seat.getStatus().equals(EStatusSeat.ACTIVE))
                throw new WebsocketException("This seat is currently inactive");
            ticket.setUser(user);
            ticket.setStatus(EStatusTicket.BOOKED);
            // calculate price:
            if (seat.getType() == ETypeSeat.vip)
                price += 30000;
            ticket.setPrice(price);
            ticket.setCreatedAt(Instant.now());
            bookingRepository.save(ticket);
        }
        return new BookingRequest(seatNames, showtimeId);
    }

    @Override
    public void updateTicket(Long id, TicketDTO newTicket) {

    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = bookingRepository.findById(id).orElse(null);
        if (ticket == null) throw new NotFoundException("Not found ticket by id: " + id);
        bookingRepository.delete(ticket);
    }

    @Override
    public void cancelExpiredTickets() {
        Instant now = Instant.now();
        List<Ticket> tickets = bookingRepository.findTicketsByCreatedAtLessThanEqualAndStatus(now.minusSeconds(600), EStatusTicket.BOOKED);
        for (Ticket ticket : tickets) {
            ticket.setStatus(EStatusTicket.UN_BOOKED);
            ticket.setUser(null);
        }
        bookingRepository.saveAll(tickets);
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = bookingRepository.findAll();
        return tickets
                .stream()
                .map(TicketDTO::new)
                .toList();
    }
}
