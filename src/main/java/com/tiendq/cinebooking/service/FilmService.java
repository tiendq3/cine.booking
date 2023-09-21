package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.FilmDTO;
import com.tiendq.cinebooking.model.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public interface FilmService {
    Page<FilmDTO> search(String key, int page, int size, Sort.Direction sort, String... properties);

    Set<FilmDTO> getAllUpComingFilm(Integer days);

    Set<FilmDTO> getAllFilms();

    FilmDTO getFilmById(Long id);

    void insertFilm(FilmDTO filmDTO, MultipartFile[] files);

    void updateFilm(Long id, FilmDTO filmDTO);

    void deleteFilm(Long ids);

    List<Film> favoriteMovie();
}
