package com.sandesh.notesapi.controller;

import com.sandesh.notesapi.dto.LoginRequest;
import com.sandesh.notesapi.dto.RegisterRequest;
import com.sandesh.notesapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        log.info("Received register request for email={}", request.getEmail());
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        log.info("Received login request for email={}", request.getEmail());
        return authService.login(request);
    }
}