package com.carbonoverde.backend.dtos.mappers;

import com.carbonoverde.backend.dtos.requests.AddressRequestDto;
import com.carbonoverde.backend.dtos.responses.AddressResponseDto;
import com.carbonoverde.backend.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDto dto) {
        return Address.builder()
                .cep(dto.getCep())
                .country(dto.getCountry())
                .state(dto.getState())
                .city(dto.getCity())
                .neighborhood(dto.getNeighborhood())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }

    public AddressResponseDto toDto(Address entity) {
        return AddressResponseDto.builder()
                .id(entity.getId())
                .cep(entity.getCep())
                .country(entity.getCountry())
                .state(entity.getState())
                .city(entity.getCity())
                .neighborhood(entity.getNeighborhood())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }
}
