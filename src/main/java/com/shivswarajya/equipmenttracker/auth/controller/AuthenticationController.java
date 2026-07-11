package com.shivswarajya.equipmenttracker.auth.controller;

import com.shivswarajya.equipmenttracker.auth.dto.LoginRequest;
import com.shivswarajya.equipmenttracker.auth.dto.LoginResponse;
import com.shivswarajya.equipmenttracker.auth.dto.RegisterRequest;
import com.shivswarajya.equipmenttracker.auth.service.AuthService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return new ApiResponse<>(
                true,
                "User registered successfully.",
                null);

    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return new ApiResponse<>(
                true,
                "Login successful",
                authService.login(request));
    }
}