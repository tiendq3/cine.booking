package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.CommentDTO;
import com.tiendq.cinebooking.security.AuthorityConstants;
import com.tiendq.cinebooking.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET COMMENT BY ID: " + id);
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public void insertComment(@RequestBody CommentDTO commentDTO) {
        log.warn("[CONTROLLER] - INSERT NEW COMMENT: " + commentDTO);
        commentService.insertComment(commentDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(AuthorityConstants.USER)
    public void updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        log.warn("[CONTROLLER] - UPDATE COMMENT: " + commentDTO);
        commentService.updateComment(id, commentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(AuthorityConstants.USER)
    public void deleteComment(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE COMMENT BY ID: " + id);
        commentService.deleteComment(id);
    }
}
