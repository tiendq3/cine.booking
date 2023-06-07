package com.tiendq.cinebooking.repository;

import com.tiendq.cinebooking.model.entities.Seat;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findSeatByRoomId(Long id, Sort sort);

    Seat findSeatByName(String name);
}
