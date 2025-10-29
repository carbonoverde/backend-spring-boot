package com.carbonoverde.backend.services;

import com.carbonoverde.backend.dtos.mappers.AddressMapper;
import com.carbonoverde.backend.dtos.mappers.CompanyMapper;
import com.carbonoverde.backend.dtos.requests.CompanyRequestDto;
import com.carbonoverde.backend.dtos.responses.CompanyMapMarker;
import com.carbonoverde.backend.dtos.responses.CompanyResponseDto;
import com.carbonoverde.backend.entities.Company;
import com.carbonoverde.backend.handlers.exceptions.BusinessException;
import com.carbonoverde.backend.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final AddressMapper addressMapper;

    public CompanyResponseDto createCompany(CompanyRequestDto companyRequest) {
        // Validar CNPJ único
        if (companyRepository.existsByCnpj(companyRequest.getCnpj())) {
            throw new BusinessException("Já existe uma empresa com este CNPJ");
        }
        Company company = companyMapper.toEntity(companyRequest);
        Company savedCompany = companyRepository.save(company);
        return companyMapper.toDto(savedCompany);
    }

    public List<CompanyResponseDto> findAll() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    public CompanyResponseDto findById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        return companyMapper.toDto(company);
    }

    public Company findEntityById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public CompanyResponseDto updateCompany(Long id, CompanyRequestDto companyRequest) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        existingCompany.setName(companyRequest.getName());
        existingCompany.setCnpj(companyRequest.getCnpj());
        existingCompany.setMonthlyEnergyConsumption(companyRequest.getMonthlyEnergyConsumption());
        existingCompany.setMonthlyWaterConsumption(companyRequest.getMonthlyWaterConsumption());
        existingCompany.setMonthlyWaste(companyRequest.getMonthlyWaste());

        Company updatedCompany = companyRepository.save(existingCompany);
        return companyMapper.toDto(updatedCompany);
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public List<CompanyResponseDto> findByCity(String city) {
        return companyRepository.findByCity(city)
                .stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CompanyResponseDto> findAllForDashboard() {
        // Retorna apenas dados básicos para o dashboard
        return companyRepository.findAll()
                .stream()
                .map(company -> CompanyResponseDto.builder()
                        .id(company.getId())
                        .name(company.getName())
                        .address(addressMapper.toDto(company.getAddress()))
                        .build())
                .collect(Collectors.toList());
    }

    public String getCompanyCity(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return company.getAddress().getCity();
    }

    public List<CompanyMapMarker> findAllForMap() {
        return companyRepository.findAll()
                .stream()
                .map(this::toMapMarker)
                .collect(Collectors.toList());
    }

    private CompanyMapMarker toMapMarker(Company company) {
        String status = "low";
        if (company.getAccumulatedCo2() > 1000) status = "high";
        else if (company.getAccumulatedCo2() > 500) status = "medium";

        return CompanyMapMarker.builder()
                .id(company.getId())
                .name(company.getName())
                .latitude(company.getAddress().getLatitude())
                .longitude(company.getAddress().getLongitude())
                .emissions(company.getAccumulatedCo2())
                .status(status)
                .build();
    }

    public Long countAll() {
        return companyRepository.count();
    }
}
