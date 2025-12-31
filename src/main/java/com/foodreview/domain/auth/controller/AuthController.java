package com.foodreview.domain.auth.controller;

import com.foodreview.domain.auth.dto.LoginRequest;
import com.foodreview.domain.auth.dto.LoginResponse;
import com.foodreview.domain.user.entity.Role;
import com.foodreview.domain.user.entity.User;
import com.foodreview.domain.user.repository.UserRepository;
import com.foodreview.global.common.ApiResponse;
import com.foodreview.global.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.debug("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            log.debug("User not found: {}", request.getEmail());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("이메일 또는 비밀번호가 올바르지 않습니다"));
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.debug("Invalid password for user: {}", request.getEmail());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("이메일 또는 비밀번호가 올바르지 않습니다"));
        }

        if (user.getRole() != Role.ADMIN) {
            log.debug("User is not admin: {}", request.getEmail());
            return ResponseEntity.status(403)
                    .body(ApiResponse.error("관리자 권한이 없습니다"));
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
        log.info("Login successful for admin: {}", request.getEmail());

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
