package com.example.backend.board.repository;

import com.example.backend.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // JPA에서 변수에 Pageable 이 들어오게 되면 페이징 파라미터라고 인식함
    Page<Board> findByTitleContainingIgnoreCaseOrWriterContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String writer,
            String content,
            Pageable pageable);
}
