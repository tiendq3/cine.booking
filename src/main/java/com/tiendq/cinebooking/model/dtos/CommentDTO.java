package com.tiendq.cinebooking.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @NotEmpty(message = "movie comment at least 20 words")
    private String comment;

    @NotNull
    private Integer rating;

    @NotNull(message = "You must be logged in and have watched this movie to rate")
    private UserDTO userDTO;

    @NotNull
    private FilmDTO filmDTO;
}
