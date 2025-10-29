package com.carbonoverde.backend.dtos.requests;

import com.carbonoverde.backend.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class UserRequestDto
{
    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 100, message = "Nome de usuário deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 4, max = 20, message = "Username deve ter entre 4 e 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
            message = "Username contém caracteres inválidos")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Senha deve conter pelo menos 1 letra maiúscula, 1 minúscula, 1 número e 1 caractere especial")
    private String password;

    @NotBlank(message = "Email é obrigatório")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$", message = "Email inválido")
    private String email;

    @NotBlank(message = "Cidade é obrigatória")
    private String city;

    @NotNull(message = "Perfil é obrigatório. Escolha entre ADMIN ou USER ou MANAGER")
    private UserRole role;
}
