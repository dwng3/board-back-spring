package com.example.backend.board.service;

import com.example.backend.board.dto.BoardRequest;
import com.example.backend.board.dto.BoardResponse;
import com.example.backend.board.entity.Board;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Page<BoardResponse> findAll(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (keyword == null || keyword.isBlank()) {
            return boardRepository.findAll(pageable)
                    .map(BoardResponse::from);
        }

        return boardRepository
                .findByTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseOrContentContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        pageable
                )
                .map(BoardResponse::from);
    }

    public BoardResponse findById(Long id) {
        return BoardResponse.from(getBoard(id));
    }

    @Transactional
    public BoardResponse create(BoardRequest request, CustomUserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다."));

        Board board = new Board(request.title(), request.content(), user);
        return BoardResponse.from(boardRepository.save(board));
    }

    @Transactional
    public BoardResponse update(Long id, BoardRequest request, CustomUserDetails userDetails) {
        Board board = getBoard(id);
        validateOwner(board, userDetails);
        board.update(request.title(), request.content());
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
    public void delete(Long id, CustomUserDetails userDetails) {
        Board board = getBoard(id);
        validateOwner(board, userDetails);
        boardRepository.delete(board);
    }

    private void validateOwner(Board board, CustomUserDetails userDetails) {
        if (userDetails == null || !board.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("본인 게시물이 아닙니다.");
        }
    }

    private Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found. id=" + id));
    }
}
