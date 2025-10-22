package com.carbonoverde.backend.entities;

import com.carbonoverde.backend.enums.StatusCompensation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Compensations")
public class Compensation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "status_ compensation", nullable = false)
    private StatusCompensation statusCompensation;

    // Dados da compensação
    @Column(name = "area_compensated_m2")
    private Double areaCompensated_M2;

    @Column(name = "co2_compensated_tons")
    private Double co2CompensatedTons;

    @Column(name = "invested_value")
    private Double investedValue;

    @Column(name = "compensation_date")
    private LocalDateTime compensationDate;

    @Column(name = "observations", length = 500)
    private String observations;

    // RELACIONAMENTOS
    // MUITA compensação para UMA empresa
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // MUITAS compensações para UMA área verde
    @ManyToOne
    @JoinColumn(name = "green_area_id", nullable = false)
    private GreenArea greenArea;

    // MUITAS compensações para MUITAS emissões mensais
    @ManyToMany
    @JoinTable(name = "compensation_emissions", joinColumns = @JoinColumn(name = "compensation_id"), inverseJoinColumns = @JoinColumn(name = "emission_id"))
    private List<MonthlyEmission> emissionsCompensadas = new ArrayList<>();

    @PrePersist
    protected void onCreate()
    {
        compensationDate = LocalDateTime.now();
        if (statusCompensation == null)
        {
            statusCompensation = StatusCompensation.PENDENTE;
        }
    }
}
