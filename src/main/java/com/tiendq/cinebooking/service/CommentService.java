package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.dtos.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDTO getCommentById(Long id);

    void insertComment(CommentDTO commentDTO);

    void updateComment(Long id, CommentDTO commentDTO);

    void deleteComment(Long id);
}
