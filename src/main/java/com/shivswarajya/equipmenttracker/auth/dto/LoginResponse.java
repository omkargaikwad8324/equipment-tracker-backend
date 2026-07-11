package com.shivswarajya.equipmenttracker.auth.dto;

import com.shivswarajya.equipmenttracker.auth.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;

    private String username;

    private String fullName;

    private Role role;
}