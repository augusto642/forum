package com.alura.forum.controller;

import com.alura.forum.dto.AuthRequestDTO;
import com.alura.forum.dto.AuthResponseDTO;
import com.alura.forum.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody @Validated AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
