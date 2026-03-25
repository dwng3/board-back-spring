package com.example.backend.board.service;

import com.example.backend.board.dto.BoardRequest;
import com.example.backend.board.dto.BoardResponse;
import com.example.backend.board.entity.Board;
import com.example.backend.board.repository.BoardRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final PasswordEncoder passwordEncoder;

	public BoardService(BoardRepository boardRepository, PasswordEncoder passwordEncoder) {
		this.boardRepository = boardRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<BoardResponse> findAll() {
		return boardRepository.findAll()
				.stream()
				.map(BoardResponse::from)
				.toList();
	}

	public BoardResponse findById(Long id) {
		return BoardResponse.from(getBoard(id));
	}

	@Transactional
	public BoardResponse create(BoardRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());
		Board board = new Board(request.title(), request.content(), request.writer(), encodedPassword);
		return BoardResponse.from(boardRepository.save(board));
	}

	@Transactional
	public BoardResponse update(Long id, BoardRequest request) {
		Board board = getBoard(id);
		board.update(request.title(), request.content(), request.writer());
		return BoardResponse.from(board);
	}

	@Transactional
	public BoardResponse updateViewCount(Long id) {
		Board board = getBoard(id);
		board.updateViewCount(board.getViewCount() + 1);
		return BoardResponse.from(board);
	}

	@Transactional
	public BoardResponse updateLikeCount(Long id) {
		Board board = getBoard(id);
		board.updateLikeCount(board.getLikeCount() + 1);
		return BoardResponse.from(board);
	}

	@Transactional
	public void delete(Long id, String password) {
		Board board = getBoard(id);

		if (!passwordEncoder.matches(password, board.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		boardRepository.delete(board);
	}

	@Transactional(readOnly = true)
	public void checkBoardPassword(Long id, String password) {
		Board board = getBoard(id);

		if (!passwordEncoder.matches(password, board.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}

	private Board getBoard(Long id) {
		return boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Board not found. id=" + id));
	}
}
