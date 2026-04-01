package com.example.backend.comment.dto;

public record CommentRequest(
    Long boardId,
    String content
) {
}
