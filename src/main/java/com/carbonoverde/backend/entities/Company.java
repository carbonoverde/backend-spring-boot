package com.carbonoverde.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;

    @NotNull
    @Column(name = "date_registration")
    private LocalDateTime dateRegistration;


    // Dados atuais da empresa (para cálculo)
    @Column(name = "accumulated_Co2_emissions")
    private Double accumulatedCo2 = 0.0; // Total não compensado

    @Column(name = "monthly_energy_consumption")
    private Double monthlyEnergyConsumption;

    @Column(name = "monthly_water_consumption")
    private Double monthlyWaterConsumption;

    @Column(name = "monthly_waste")
    private Double monthlyWaste;


    // RELACIONAMENTOS
    // Uma empresa tem UM endereço
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Uma empresa tem MUITAS emissões mensais
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<MonthlyEmission> monthlyEmissions = new ArrayList<>();

    // Uma empresa tem MUITAS compensações
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Compensation> compensations = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        dateRegistration = LocalDateTime.now();
    }

    // Métod para adicionar emissão mensal
    public void addEmissionsMonthly(Double emissionsCo2)
    {
        if (this.accumulatedCo2 == null) {
            this.accumulatedCo2 = 0.0;
        }
        if (emissionsCo2 != null) {
            this.accumulatedCo2 += emissionsCo2;
        }
    }
}
