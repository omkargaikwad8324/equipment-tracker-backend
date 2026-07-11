package com.shivswarajya.equipmenttracker.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivswarajya.equipmenttracker.util.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                false,
                "Unauthorized. Please login first.",
                null
        );

        new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
    }
}