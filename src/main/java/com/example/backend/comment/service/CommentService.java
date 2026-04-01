package com.example.backend.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.comment.dto.CommentRequest;
import com.example.backend.comment.dto.CommentResponse;
import com.example.backend.comment.entity.Comment;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.security.CustomUserDetails;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    // boardId에 적힌 댓글 전체 조회
    public List<CommentResponse> findAllByBoardId(Long boardId) {
        return commentRepository.findAllByBoardId(boardId)
                .stream()
                .map(comment -> CommentResponse.from(comment))
                .toList();
    }

    // 댓글 작성
    public CommentResponse create(CommentRequest request, CustomUserDetails userDetails){
        String writer = userDetails.getUsername();
        Comment comment = new Comment(writer, request.content(), request.boardId());

        return CommentResponse.from(commentRepository.save(comment));
    }

}
