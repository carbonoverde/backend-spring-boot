package com.carbonoverde.backend.dtos.responses;

import com.carbonoverde.backend.enums.StatusArea;
import com.carbonoverde.backend.enums.TypeArea;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GreenAreaResponseDto
{
    private Long id;
    private String name;
    private Double areaTotalM2;
    private Double areaAvailableM2;
    private TypeArea typeArea;
    private StatusArea statusArea;
    private String responsible;
    private LocalDateTime dataCadastro;
    private AddressResponseDto address;
}
