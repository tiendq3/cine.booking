package com.tiendq.cinebooking.repository;

import com.tiendq.cinebooking.model.entities.Seat;
import com.tiendq.cinebooking.model.entities.Showtime;
import com.tiendq.cinebooking.model.entities.Ticket;
import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.model.enums.EStatusTicket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Ticket, Long> {

    Ticket findTicketBySeatNameAndShowtime(String seatName, Showtime showtime);

    List<Ticket> findTicketByShowtimeId(Long showtimeId, Sort sort);

    List<Ticket> findTicketsByUser(User user);

    List<Ticket> findTicketsByCreatedAtLessThanEqualAndStatus(Instant time, EStatusTicket status);

    Ticket findTicketByShowtimeAndSeat(Showtime showtime, Seat seat);
}
