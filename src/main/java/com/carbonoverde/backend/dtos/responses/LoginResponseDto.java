package com.carbonoverde.backend.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto
{
    private String token;
    private String username;
    private String role;
    private String clientName;
}
