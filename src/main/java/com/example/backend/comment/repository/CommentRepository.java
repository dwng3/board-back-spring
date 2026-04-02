package com.example.backend.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findAllByBoard_IdOrderByIdDesc(Long boardId);
}
