package com.sandesh.notesapi.service;

import com.sandesh.notesapi.dto.LoginRequest;
import com.sandesh.notesapi.dto.RegisterRequest;
import com.sandesh.notesapi.entity.User;
import com.sandesh.notesapi.repository.UserRepository;
import com.sandesh.notesapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        log.info("Attempting user registration for email={}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Registration blocked, email already registered: {}", request.getEmail());
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        log.info("User registered successfully for email={}", request.getEmail());

        return "User Registered Successfully";
    }

    public String login(LoginRequest request) {
        log.info("Attempting login for email={}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        log.debug("User record found for email={}", user.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Invalid password attempt for email={}", request.getEmail());
            throw new IllegalArgumentException("Invalid password");
        }

        log.info("Login successful for email={}", request.getEmail());
        return jwtUtil.generateToken(user.getEmail());
    }
}