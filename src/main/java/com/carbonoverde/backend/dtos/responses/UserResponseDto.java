package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto
{
    private Long id;
    private String name;
    private String username;
    private String email;
    private String role;
}
