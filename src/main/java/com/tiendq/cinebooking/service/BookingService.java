package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.TicketDTO;
import com.tiendq.cinebooking.model.request.BookingRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    Page<TicketDTO> getPagingTicket(int page, int size);

    TicketDTO getTicketById(Long id);

    List<TicketDTO> getTicketsByCurrentUser();

    List<TicketDTO> getTicketsByShowtimeId(Long showtimeId);

    List<TicketDTO> getTicketsByRoomOfShowtime(Long showtimeId, Long roomId);

    BookingRequest bookingTicket(String[] seatNames, Long showtimeId, String token);

    void updateTicket(Long id, TicketDTO newTicket);

    void deleteTicket(Long id);

    void cancelExpiredTickets();

    List<TicketDTO> getAllTickets();
}
