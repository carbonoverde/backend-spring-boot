package com.carbonoverde.backend.dtos.mappers;

import com.carbonoverde.backend.dtos.requests.UserRequestDto;
import com.carbonoverde.backend.dtos.responses.UserResponseDto;
import com.carbonoverde.backend.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserRequestDto dto) {
        return User.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(dto.getRole())
                .city(dto.getCity())
                .build();
    }

    public UserResponseDto toDto(User entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .role(entity.getRole().name())
                .city(entity.getCity())
                .build();
    }

}
