package com.example.backend.board.repository;

import com.example.backend.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContainingIgnoreCaseOrUser_NicknameContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String username,
            String content,
            Pageable pageable
    );
}
