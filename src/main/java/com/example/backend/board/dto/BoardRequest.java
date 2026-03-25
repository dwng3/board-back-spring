package com.example.backend.board.dto;

public record BoardRequest(
		String title,
		String content,
		String writer,
		String password
) {
}
