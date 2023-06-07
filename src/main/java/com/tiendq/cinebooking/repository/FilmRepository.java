package com.tiendq.cinebooking.repository;

import com.tiendq.cinebooking.model.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query(value = "SELECT * FROM films WHERE (:key IS NULL OR name LIKE CONCAT('%', :key, '%')) ",
            nativeQuery = true)
    Page<Film> searchBy(@Param("key") String key, Pageable pageable);

    @Query(value = "SELECT * FROM films WHERE rate >=4",
            nativeQuery = true)
    List<Film> favoriteMovie();

    Optional<Film> findFilmByName(String name);
}
