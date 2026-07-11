package com.shivswarajya.equipmenttracker.auth.security;

import com.shivswarajya.equipmenttracker.auth.entity.User;
import com.shivswarajya.equipmenttracker.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

     return org.springframework.security.core.userdetails.User
        .builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities("ROLE_" + user.getRole().name())
        .disabled(!Boolean.TRUE.equals(user.getEnabled()))
        .build();
    }
}