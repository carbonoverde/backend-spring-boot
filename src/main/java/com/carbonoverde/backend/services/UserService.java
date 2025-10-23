package com.carbonoverde.backend.services;

import com.carbonoverde.backend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user)
    {
        //criação de usuário simples, sem validações necessárias para o exemplo
        return User.builder()
                .name(user.getName())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
