package com.example.backend.user.controller;

import com.example.backend.user.dto.SignUpRequest;
import com.example.backend.user.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
		userService.signUp(request);
		return ResponseEntity.ok("회원가입 완료");
	}

}
