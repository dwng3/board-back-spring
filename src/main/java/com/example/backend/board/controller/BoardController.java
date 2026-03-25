package com.example.backend.board.controller;

import com.example.backend.board.dto.BoardRequest;
import com.example.backend.board.dto.BoardResponse;
import com.example.backend.board.dto.PasswordCheckRequest;
import com.example.backend.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping
	public List<BoardResponse> getBoards() {
		return boardService.findAll();
	}

	@GetMapping("/{id}")
	public BoardResponse getBoard(@PathVariable Long id) {
		boardService.updateViewCount(id);
		return boardService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BoardResponse createBoard(@RequestBody BoardRequest request) {
		return boardService.create(request);
	}

	@PutMapping("/{id}")
	public BoardResponse updateBoard(@PathVariable Long id, @RequestBody BoardRequest request) {
		return boardService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBoard(@PathVariable Long id, @RequestBody PasswordCheckRequest request ) {
		boardService.delete(id, request.password());
	}

	@PutMapping("/likes/{id}")
	public BoardResponse updateLikeCount(@PathVariable Long id) {
		return boardService.updateLikeCount(id);
	}

	@PostMapping("/check/{id}")
	public void checkBoardPassword(@PathVariable Long id, @RequestBody PasswordCheckRequest request) {
		boardService.checkBoardPassword(id, request.password());
	}
}
