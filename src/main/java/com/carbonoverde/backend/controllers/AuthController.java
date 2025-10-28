package com.carbonoverde.backend.controllers;

import com.carbonoverde.backend.configs.security.JwtService;
import com.carbonoverde.backend.dtos.requests.LoginRequestDto;
import com.carbonoverde.backend.dtos.responses.LoginResponseDto;
import com.carbonoverde.backend.entities.User;
import com.carbonoverde.backend.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(userDetails);

        User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .clientName(user.getName())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
