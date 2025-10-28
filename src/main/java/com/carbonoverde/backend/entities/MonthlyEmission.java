package com.carbonoverde.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monthly_emissions")
public class MonthlyEmission
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    // RELACIONAMENTOS
    // Muitas emissões mensais pertencem a UMA empresa
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @NotBlank
    @Column(name = "month_reference", nullable = false)
    private YearMonth monthReference; // Ex: 2024-01

    // Dados específicos do mês
    @Column(name = "emissions_co2")
    private Double emissionsCo2;

    @Column(name = "consumption_energy")
    private Double consumptionEnergy;

    @Column(name = "consumption_water")
    private Double consumptionWater;

    @Column(name = "generated_waste")
    private Double generatedWaste;

    @Column(name = "date_register")
    private LocalDateTime dateRegister;

    @Column(name = "already_compensated")
    private Boolean alreadyCompensated = false;

    @PrePersist
    protected void onCreate()
    {
        dateRegister = LocalDateTime.now();
    }
}
