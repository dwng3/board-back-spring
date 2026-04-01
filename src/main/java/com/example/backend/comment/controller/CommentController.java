package com.example.backend.comment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.comment.dto.CommentRequest;
import com.example.backend.comment.dto.CommentResponse;
import com.example.backend.comment.service.CommentService;
import com.example.backend.security.CustomUserDetails;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/api/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getComments(@RequestParam Long boardId) {
        return commentService.findAllByBoardId(boardId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@RequestBody CommentRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return commentService.create(request, userDetails);
    }


}
