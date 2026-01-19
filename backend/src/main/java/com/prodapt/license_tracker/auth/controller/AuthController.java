package com.prodapt.license_tracker.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prodapt.license_tracker.auth.dto.LoginRequest;
import com.prodapt.license_tracker.auth.dto.LoginResponse;
import com.prodapt.license_tracker.auth.security.JwtUtil;
import com.prodapt.license_tracker.auth.service.AuthService;
import com.prodapt.license_tracker.user.entity.User;
import com.prodapt.license_tracker.user.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

		return ResponseEntity.ok(authService.login(request));
	}
}
