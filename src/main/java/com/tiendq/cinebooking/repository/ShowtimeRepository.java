package com.tiendq.cinebooking.repository;

import com.tiendq.cinebooking.model.entities.Room;
import com.tiendq.cinebooking.model.entities.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    @Query(value = "select * from showtimes where start_time <= :time and start_time >= now()",
            nativeQuery = true)
    List<Showtime> getAllShowtimeByTime(@Param("time") Instant time);

    List<Showtime> findShowtimeByFilmId(Long filmId);

    List<Showtime> findShowtimeByRoom(Room room);

    List<Showtime> findShowtimesByFilmIdAndStartTimeGreaterThan(Long filmId, Instant time);
}
