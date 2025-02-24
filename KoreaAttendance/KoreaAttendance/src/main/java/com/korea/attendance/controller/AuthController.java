package com.korea.attendance.controller;

import com.korea.attendance.model.User;
import com.korea.attendance.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/{userId}")  // ✅ URL 매핑 수정
    public User getUserRole(@PathVariable("userId") String userId) {
        return authService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
