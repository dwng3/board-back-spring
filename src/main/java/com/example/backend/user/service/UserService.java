package com.example.backend.user.service;

import com.example.backend.user.dto.SignUpRequest;
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void signUp(SignUpRequest request) {

		if (userRepository.existsByUsername(request.username())) {
			throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
		}

		String encodedPassword = passwordEncoder.encode(request.password());

		User user = new User(request.username(), encodedPassword, request.email(), request.nickname(), "USER");

		userRepository.save(user);
	}
}
