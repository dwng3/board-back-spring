package com.example.backend.board.controller;

import com.example.backend.board.dto.BoardRequest;
import com.example.backend.board.dto.BoardResponse;
import com.example.backend.board.service.BoardService;
import com.example.backend.security.CustomUserDetails;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping
	public Page<BoardResponse> getBoards(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(required = false) String keyword
	) {
		return boardService.findAll(page, size, keyword);
	}

	@GetMapping("/{id}")
	public BoardResponse getBoard(@PathVariable Long id) {
		boardService.updateViewCount(id);
		return boardService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BoardResponse createBoard(@RequestBody BoardRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return boardService.create(request, userDetails);
	}

	@PutMapping("/{id}")
	public BoardResponse updateBoard(@PathVariable Long id, @RequestBody BoardRequest request) {
		return boardService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBoard(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails ) {
		boardService.delete(id, userDetails);
	}

	@PutMapping("/likes/{id}")
	public BoardResponse updateLikeCount(@PathVariable Long id) {
		return boardService.updateLikeCount(id);
	}

}
