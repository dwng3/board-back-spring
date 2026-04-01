package com.example.backend.comment.dto;

import java.time.LocalDateTime;

import com.example.backend.comment.entity.Comment;

public record CommentResponse(
    Long id,
    String writer,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return  new CommentResponse(
            comment.getId(),
            comment.getWriter(),
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
            );
    }
}
