package com.carbonoverde.backend.dtos.mappers;

import com.carbonoverde.backend.dtos.requests.GreenAreaRequestDto;
import com.carbonoverde.backend.dtos.responses.GreenAreaResponseDto;
import com.carbonoverde.backend.entities.GreenArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GreenAreaMapper {

    private final AddressMapper addressMapper;

    public GreenArea toEntity(GreenAreaRequestDto dto) {
        GreenArea greenArea = new GreenArea();
        greenArea.setName(dto.getName());
        greenArea.setAreaTotalM2(dto.getAreaTotalM2());
        greenArea.setTypeArea(dto.getTypeArea());
        greenArea.setStatusArea(dto.getStatusArea());
        greenArea.setResponsible(dto.getResponsible());
        greenArea.setAddress(addressMapper.toEntity(dto.getAddress()));
        return greenArea;
    }

    public GreenAreaResponseDto toDto(GreenArea entity) {
        return GreenAreaResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .areaTotalM2(entity.getAreaTotalM2())
                .areaAvailableM2(entity.getAreaAvailableM2())
                .typeArea(entity.getTypeArea())
                .statusArea(entity.getStatusArea())
                .responsible(entity.getResponsible())
                .dataCadastro(entity.getDataCadastro())
                .address(addressMapper.toDto(entity.getAddress()))
                .build();
    }
}
