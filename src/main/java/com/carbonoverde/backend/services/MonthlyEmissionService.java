package com.carbonoverde.backend.services;

import com.carbonoverde.backend.dtos.requests.MonthlyEmissionRequestDto;
import com.carbonoverde.backend.dtos.responses.MonthlyEmissionMapMarker;
import com.carbonoverde.backend.dtos.responses.MonthlyEmissionResponseDto;
import com.carbonoverde.backend.entities.Company;
import com.carbonoverde.backend.entities.MonthlyEmission;
import com.carbonoverde.backend.dtos.mappers.MonthlyEmissionMapper;
import com.carbonoverde.backend.repositories.MonthlyEmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlyEmissionService {

    private final MonthlyEmissionRepository monthlyEmissionRepository;
    private final MonthlyEmissionMapper monthlyEmissionMapper;
    private final CompanyService companyService;

    @Transactional
    public MonthlyEmissionResponseDto createMonthlyEmission(MonthlyEmissionRequestDto emissionRequest) {
        MonthlyEmission emission = monthlyEmissionMapper.toEntity(emissionRequest);

        // Atualizar CO2 acumulado da empresa
        Company company = companyService.findEntityById(emissionRequest.getCompanyId());
        company.addEmissionsMonthly(emissionRequest.getEmissionsCo2());
        companyService.save(company); // Precisamos adicionar este método no CompanyService

        MonthlyEmission savedEmission = monthlyEmissionRepository.save(emission);
        return monthlyEmissionMapper.toDto(savedEmission);
    }

    public List<MonthlyEmissionResponseDto> findAll() {
        return monthlyEmissionRepository.findAll()
                .stream()
                .map(monthlyEmissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public MonthlyEmissionResponseDto findById(Long id) {
        MonthlyEmission emission = monthlyEmissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MonthlyEmission not found with id: " + id));
        return monthlyEmissionMapper.toDto(emission);
    }

    public List<MonthlyEmissionResponseDto> findByCompanyId(Long companyId) {
        return monthlyEmissionRepository.findByCompanyId(companyId)
                .stream()
                .map(monthlyEmissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public MonthlyEmissionResponseDto updateMonthlyEmission(Long id, MonthlyEmissionRequestDto emissionRequest) {
        MonthlyEmission existingEmission = monthlyEmissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MonthlyEmission not found with id: " + id));

        Double oldCo2Value = existingEmission.getEmissionsCo2();
        Long oldCompanyId = existingEmission.getCompany().getId();
        Double newCo2Value = emissionRequest.getEmissionsCo2();
        Long newCompanyId = emissionRequest.getCompanyId();

        // Lógica para ajustar CO2 acumulado se necessário
        if (!oldCompanyId.equals(newCompanyId) || !oldCo2Value.equals(newCo2Value)) {
            adjustAccumulatedCo2(oldCompanyId, newCompanyId, oldCo2Value, newCo2Value);
        }

        // Atualizar a empresa se necessário
        if (!oldCompanyId.equals(newCompanyId)) {
            Company newCompany = companyService.findEntityById(newCompanyId);
            existingEmission.setCompany(newCompany);
        }

        existingEmission.setMonthReference(emissionRequest.getMonthReference());
        existingEmission.setEmissionsCo2(newCo2Value);
        existingEmission.setConsumptionEnergy(emissionRequest.getConsumptionEnergy());
        existingEmission.setConsumptionWater(emissionRequest.getConsumptionWater());
        existingEmission.setGeneratedWaste(emissionRequest.getGeneratedWaste());

        MonthlyEmission updatedEmission = monthlyEmissionRepository.save(existingEmission);
        return monthlyEmissionMapper.toDto(updatedEmission);
    }

    public void deleteMonthlyEmission(Long id) {
        MonthlyEmission emission = monthlyEmissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MonthlyEmission not found with id: " + id));

        // Remover o CO2 da emissão do acumulado da empresa ao deletar
        Company company = emission.getCompany();
        company.setAccumulatedCo2(company.getAccumulatedCo2() - emission.getEmissionsCo2());
        companyService.save(company);

        monthlyEmissionRepository.deleteById(id);
    }

    private void adjustAccumulatedCo2(Long oldCompanyId, Long newCompanyId, Double oldCo2Value, Double newCo2Value) {
        // Se a empresa mudou
        if (!oldCompanyId.equals(newCompanyId)) {
            // Remover CO2 da empresa antiga
            Company oldCompany = companyService.findEntityById(oldCompanyId);
            oldCompany.setAccumulatedCo2(oldCompany.getAccumulatedCo2() - oldCo2Value);
            companyService.save(oldCompany);

            // Adicionar CO2 à nova empresa
            Company newCompany = companyService.findEntityById(newCompanyId);
            newCompany.setAccumulatedCo2(newCompany.getAccumulatedCo2() + newCo2Value);
            companyService.save(newCompany);
        } else {
            // Apenas o valor do CO2 mudou na mesma empresa
            Company company = companyService.findEntityById(oldCompanyId);
            Double difference = newCo2Value - oldCo2Value;
            company.setAccumulatedCo2(company.getAccumulatedCo2() + difference);
            companyService.save(company);
        }
    }

    public Long countAll() {
        return monthlyEmissionRepository.count();
    }

    public Double getTotalCO2Emitted() {
        return monthlyEmissionRepository.sumCO2Emitted();
    }

    public List<MonthlyEmissionResponseDto> findByUserCity(String userCity) {
        return monthlyEmissionRepository.findByCompanyCity(userCity)
                .stream()
                .map(monthlyEmissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MonthlyEmissionMapMarker> findAllForMap() {
        return monthlyEmissionRepository.findAll()
                .stream()
                .map(this::toMapMarker)
                .collect(Collectors.toList());
    }

    private MonthlyEmissionMapMarker toMapMarker(MonthlyEmission emission) {
        String status = "low";
        if (emission.getEmissionsCo2() > 100) status = "high";
        else if (emission.getEmissionsCo2() > 50) status = "medium";

        return MonthlyEmissionMapMarker.builder()
                .id(emission.getId())
                .companyName(emission.getCompany().getName())
                .latitude(emission.getCompany().getAddress().getLatitude())
                .longitude(emission.getCompany().getAddress().getLongitude())
                .emissions(emission.getEmissionsCo2())
                .monthReference(emission.getMonthReference())
                .status(status)
                .build();
    }
}
