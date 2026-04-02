package com.example.backend.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.board.entity.Board;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.comment.dto.CommentRequest;
import com.example.backend.comment.dto.CommentResponse;
import com.example.backend.comment.entity.Comment;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // boardId에 적힌 댓글 전체 조회
    public List<CommentResponse> findAllByBoardId(Long boardId) {
        return commentRepository.findAllByBoard_IdOrderByIdDesc(boardId)
                .stream()
                .map(comment -> CommentResponse.from(comment))
                .toList();
    }

    @Transactional
    public CommentResponse create(CommentRequest request, CustomUserDetails userDetails) {
        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
    
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다."));
    
        Comment comment = new Comment(board, user, request.content());
        return CommentResponse.from(commentRepository.save(comment));
    }


}
