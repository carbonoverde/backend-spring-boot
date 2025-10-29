package com.carbonoverde.backend.services;

import com.carbonoverde.backend.dtos.mappers.CompensationMapper;
import com.carbonoverde.backend.dtos.requests.CompensationRequestDto;
import com.carbonoverde.backend.dtos.responses.CompensationMapMarker;
import com.carbonoverde.backend.dtos.responses.CompensationResponseDto;
import com.carbonoverde.backend.dtos.responses.RecentActivity;
import com.carbonoverde.backend.entities.Compensation;
import com.carbonoverde.backend.entities.GreenArea;
import com.carbonoverde.backend.entities.MonthlyEmission;
import com.carbonoverde.backend.handlers.exceptions.BusinessException;
import com.carbonoverde.backend.repositories.CompensationRepository;
import com.carbonoverde.backend.repositories.MonthlyEmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompensationService {

    private final CompensationRepository compensationRepository;
    private final CompensationMapper compensationMapper;
    private final MonthlyEmissionRepository monthlyEmissionRepository;
    private final GreenAreaService greenAreaService;

    public CompensationResponseDto createCompensation(CompensationRequestDto compensationRequest) {
        log.info("Criando compensação para empresa: {}, área: {}",
                compensationRequest.getCompanyId(), compensationRequest.getGreenAreaId());

        try {
        GreenArea greenArea = greenAreaService.findEntityById(compensationRequest.getGreenAreaId());
        if (!greenArea.temAreaDisponivel(compensationRequest.getAreaCompensated_M2())) {
            throw new BusinessException("Área insuficiente disponível para compensação");
        }

        greenArea.alocarArea(compensationRequest.getAreaCompensated_M2());
        greenAreaService.save(greenArea);

        Compensation compensation = compensationMapper.toEntity(compensationRequest);
        Compensation savedCompensation = compensationRepository.save(compensation);
        return compensationMapper.toDto(savedCompensation);
        } catch (Exception e) {
            log.error("Erro ao criar compensação: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CompensationResponseDto> findAll() {
        return compensationRepository.findAll()
                .stream()
                .map(compensationMapper::toDto)
                .collect(Collectors.toList());
    }

    public CompensationResponseDto findById(Long id) {
        Compensation compensation = compensationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compensation not found with id: " + id));
        return compensationMapper.toDto(compensation);
    }

    public List<CompensationResponseDto> findByCompanyId(Long companyId) {
        return compensationRepository.findAll()
                .stream()
                .filter(compensation -> compensation.getCompany().getId().equals(companyId))
                .map(compensationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CompensationResponseDto> findByGreenAreaId(Long greenAreaId) {
        return compensationRepository.findAll()
                .stream()
                .filter(compensation -> compensation.getGreenArea().getId().equals(greenAreaId))
                .map(compensationMapper::toDto)
                .collect(Collectors.toList());
    }

    public CompensationResponseDto updateCompensation(Long id, CompensationRequestDto compensationRequest) {
        Compensation existingCompensation = compensationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compensation not found with id: " + id));

        existingCompensation.setStatusCompensation(compensationRequest.getStatusCompensation());
        existingCompensation.setAreaCompensated_M2(compensationRequest.getAreaCompensated_M2());
        existingCompensation.setCo2CompensatedTons(compensationRequest.getCo2CompensatedTons());
        existingCompensation.setInvestedValue(compensationRequest.getInvestedValue());
        existingCompensation.setObservations(compensationRequest.getObservations());

        Compensation updatedCompensation = compensationRepository.save(existingCompensation);
        return compensationMapper.toDto(updatedCompensation);
    }

    public void deleteCompensation(Long id) {
        if (!compensationRepository.existsById(id)) {
            throw new RuntimeException("Compensation not found with id: " + id);
        }
        compensationRepository.deleteById(id);
    }

    public CompensationResponseDto addEmissionToCompensation(Long compensationId, Long emissionId) {
        Compensation compensation = compensationRepository.findById(compensationId)
                .orElseThrow(() -> new RuntimeException("Compensation not found with id: " + compensationId));

        MonthlyEmission emission = monthlyEmissionRepository.findById(emissionId)
                .orElseThrow(() -> new RuntimeException("MonthlyEmission not found with id: " + emissionId));

        compensation.getEmissionsCompensadas().add(emission);
        emission.setAlreadyCompensated(true);

        Compensation updatedCompensation = compensationRepository.save(compensation);
        monthlyEmissionRepository.save(emission);

        return compensationMapper.toDto(updatedCompensation);
    }

    public List<CompensationMapMarker> findAllForMap() {
        return compensationRepository.findAll()
                .stream()
                .map(this::toMapMarker)
                .collect(Collectors.toList());
    }

    private CompensationMapMarker toMapMarker(Compensation compensation) {
        return CompensationMapMarker.builder()
                .id(compensation.getId())
                .companyName(compensation.getCompany().getName())
                .greenAreaName(compensation.getGreenArea().getName())
                .latitude(compensation.getGreenArea().getAddress().getLatitude())
                .longitude(compensation.getGreenArea().getAddress().getLongitude())
                .co2Compensated(compensation.getCo2CompensatedTons())
                .status(compensation.getStatusCompensation().name())
                .build();
    }

    public Long countAll() {
        return compensationRepository.count();
    }

    public Double getTotalCO2Compensated() {
        return compensationRepository.sumCO2Compensated();
    }

    public Double getTotalInvested() {
        return compensationRepository.sumInvestedValue();
    }

    public List<RecentActivity> findRecentActivities() {
        return compensationRepository.findTop10ByOrderByCompensationDateDesc()
                .stream()
                .map(this::toRecentActivity)
                .collect(Collectors.toList());
    }

    private RecentActivity toRecentActivity(Compensation compensation) {
        return RecentActivity.builder()
                .id(compensation.getId())
                .type("COMPENSATION")
                .description(String.format("Compensação de %.2f ton CO2", compensation.getCo2CompensatedTons()))
                .timestamp(compensation.getCompensationDate())
                .companyName(compensation.getCompany().getName())
                .status(compensation.getStatusCompensation().name())
                .build();
    }

    public Double getTotalAreaCompensated() {
        return compensationRepository.sumAreaCompensated();
    }

    public List<CompensationResponseDto> findByUserCity(String userCity) {
        return compensationRepository.findByUserCity(userCity)
                .stream()
                .map(compensationMapper::toDto)
                .collect(Collectors.toList());
    }
}
