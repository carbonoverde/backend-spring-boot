package com.carbonoverde.backend.services;

import com.carbonoverde.backend.dtos.mappers.AddressMapper;
import com.carbonoverde.backend.dtos.mappers.GreenAreaMapper;
import com.carbonoverde.backend.dtos.requests.GreenAreaRequestDto;
import com.carbonoverde.backend.dtos.responses.GreenAreaMapMarker;
import com.carbonoverde.backend.dtos.responses.GreenAreaResponseDto;
import com.carbonoverde.backend.entities.GreenArea;
import com.carbonoverde.backend.repositories.GreenAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GreenAreaService {

    private final GreenAreaRepository greenAreaRepository;
    private final GreenAreaMapper greenAreaMapper;
    private final AddressMapper addressMapper;

    public GreenAreaResponseDto createGreenArea(GreenAreaRequestDto greenAreaRequest) {
        GreenArea greenArea = greenAreaMapper.toEntity(greenAreaRequest);
        GreenArea savedGreenArea = greenAreaRepository.save(greenArea);
        return greenAreaMapper.toDto(savedGreenArea);
    }

    public List<GreenAreaResponseDto> findAll() {
        return greenAreaRepository.findAll()
                .stream()
                .map(greenAreaMapper::toDto)
                .collect(Collectors.toList());
    }

    public GreenAreaResponseDto findById(Long id) {
        GreenArea greenArea = greenAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GreenArea not found with id: " + id));
        return greenAreaMapper.toDto(greenArea);
    }

    public GreenArea findEntityById(Long id) {
        return greenAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GreenArea not found with id: " + id));
    }

    public GreenAreaResponseDto updateGreenArea(Long id, GreenAreaRequestDto greenAreaRequest) {
        GreenArea existingGreenArea = greenAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GreenArea not found with id: " + id));

        existingGreenArea.setName(greenAreaRequest.getName());
        existingGreenArea.setAreaTotalM2(greenAreaRequest.getAreaTotalM2());
        existingGreenArea.setTypeArea(greenAreaRequest.getTypeArea());
        existingGreenArea.setStatusArea(greenAreaRequest.getStatusArea());
        existingGreenArea.setResponsible(greenAreaRequest.getResponsible());

        GreenArea updatedGreenArea = greenAreaRepository.save(existingGreenArea);
        return greenAreaMapper.toDto(updatedGreenArea);
    }

    public void deleteGreenArea(Long id) {
        if (!greenAreaRepository.existsById(id)) {
            throw new RuntimeException("GreenArea not found with id: " + id);
        }
        greenAreaRepository.deleteById(id);
    }

    public GreenArea save(GreenArea greenArea) {
        return greenAreaRepository.save(greenArea);
    }

    public List<GreenAreaMapMarker> findAllForMap() {
        return greenAreaRepository.findActiveAreas()
                .stream()
                .map(this::toMapMarker)
                .collect(Collectors.toList());
    }

    private GreenAreaMapMarker toMapMarker(GreenArea greenArea) {
        return GreenAreaMapMarker.builder()
                .id(greenArea.getId())
                .name(greenArea.getName())
                .latitude(greenArea.getAddress().getLatitude())
                .longitude(greenArea.getAddress().getLongitude())
                .areaAvailable(greenArea.getAreaAvailableM2())
                .type(greenArea.getTypeArea().name())
                .status(greenArea.getStatusArea().name())
                .build();
    }

    public Long countAll() {
        return greenAreaRepository.count();
    }

    public List<GreenAreaResponseDto> findByCity(String city) {
        return greenAreaRepository.findByCity(city)
                .stream()
                .map(greenAreaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GreenAreaResponseDto> findAllForDashboard() {
        // Para o dashboard, retornamos apenas áreas ativas com informações básicas
        return greenAreaRepository.findActiveAreas()
                .stream()
                .map(this::toDashboardDto)
                .collect(Collectors.toList());
    }

    private GreenAreaResponseDto toDashboardDto(GreenArea greenArea) {
        return GreenAreaResponseDto.builder()
                .id(greenArea.getId())
                .name(greenArea.getName())
                .areaTotalM2(greenArea.getAreaTotalM2())
                .areaAvailableM2(greenArea.getAreaAvailableM2())
                .typeArea(greenArea.getTypeArea())
                .statusArea(greenArea.getStatusArea())
                .responsible(greenArea.getResponsible())
                .dataCadastro(greenArea.getDataCadastro())
                .address(addressMapper.toDto(greenArea.getAddress()))
                .build();
    }
}
