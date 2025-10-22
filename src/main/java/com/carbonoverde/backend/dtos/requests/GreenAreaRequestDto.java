package com.carbonoverde.backend.dtos.requests;

import com.carbonoverde.backend.enums.StatusArea;
import com.carbonoverde.backend.enums.TypeArea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GreenAreaRequestDto
{
    @NotBlank(message = "Nome da área verde é obrigatório")
    @Size(min = 2, max = 100, message = "Nome da área verde deve ter entre 2 e 100 caracteres")
    private String name;

    @NotNull(message = "Área total é obrigatória")
    @Positive(message = "Área total deve ser um valor positivo")
    private Double areaTotalM2;

    @NotNull(message = "Tipo de área é obrigatório")
    private TypeArea typeArea;

    @NotNull(message = "Status da área é obrigatório")
    private StatusArea statusArea;

    @NotBlank(message = "Responsável é obrigatório")
    @Size(max = 100, message = "Responsável deve ter no máximo 100 caracteres")
    private String responsible;

    @NotNull(message = "Endereço é obrigatório")
    private AddressRequestDto address;
}
