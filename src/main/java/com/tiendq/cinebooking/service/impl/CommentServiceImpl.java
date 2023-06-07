package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.exception.UnAuthorityException;
import com.tiendq.cinebooking.model.dtos.CommentDTO;
import com.tiendq.cinebooking.model.entities.Comment;
import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.repository.CommentRepository;
import com.tiendq.cinebooking.repository.UserRepository;
import com.tiendq.cinebooking.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public CommentDTO getCommentById(Long id) {
        log.warn("[SERVICE] - GET COMMENT BY ID: " + id);
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) throw new NotFoundException("not found comment by id: " + id);
        return modelMapper.map(comment, CommentDTO.class);
    }

    @Transactional
    @Override
    public void insertComment(CommentDTO commentDTO) {
        log.warn("[SERVICE] - INSERT COMMENT: " + commentDTO);

    }

    @Override
    public void updateComment(Long id, CommentDTO commentDTO) {
        log.warn("[SERVICE] - UPDATE COMMENT: " + commentDTO);
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) throw new NotFoundException("not found comment by id: " + id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(authentication.getName());
        if (!user.equals(comment.getUser()))
            throw new UnAuthorityException("Not allowed to edit other people's comments");
        comment.setComment(commentDTO.getComment());
        comment.setRating(commentDTO.getRating());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        log.warn("[SERVICE] - DELETE COMMENT BY ID: " + id);
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) throw new NotFoundException("not found comment by id: " + id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(authentication.getName());
        if (!user.equals(comment.getUser()))
            throw new UnAuthorityException("Not allowed to delete other people's comments");
        commentRepository.delete(comment);
    }
}
