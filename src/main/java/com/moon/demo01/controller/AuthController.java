package com.moon.demo01.controller;

import com.moon.demo01.dto.LoginRequest;
import com.moon.demo01.service.UserService;
import com.moon.demo01.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // 사용자 인증
            boolean isAuthenticated = userService.authenticateUser(email, password);

            if (isAuthenticated) {
                // 인증 성공 시 JWT 토큰 생성
                String token = jwtUtil.generateToken(email);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Invalid email or password");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
