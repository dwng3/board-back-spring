package com.example.backend.board.dto;

import com.example.backend.board.entity.Board;

import java.time.LocalDateTime;

public record BoardResponse(
		Long id,
		String title,
		String content,
		String writer,
		Integer likeCount,
		Integer viewCount,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
	public static BoardResponse from(Board board) {
		return new BoardResponse(
				board.getId(),
				board.getTitle(),
				board.getContent(),
				board.getWriter(),
				board.getLikeCount(),
				board.getViewCount(),
				board.getCreatedAt(),
				board.getUpdatedAt()
		);
	}
}
