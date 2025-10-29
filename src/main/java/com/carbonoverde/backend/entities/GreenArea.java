package com.carbonoverde.backend.entities;

import com.carbonoverde.backend.enums.TypeArea;
import com.carbonoverde.backend.enums.StatusArea;
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
@Table(name = "green_areas")
public class GreenArea
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull
    @Column(name = "area_total", nullable = false)
    private Double areaTotalM2; // area total em m²

    @Column(name = "area_available_m2", nullable = false)
    private Double areaAvailableM2; // Área ainda não compensada

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_area", nullable = false)
    private TypeArea typeArea;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_area", nullable = false)
    private StatusArea statusArea;

    @NotBlank
    @Column(name = "responsible", nullable = false, length = 100)
    private String responsible;

    @NotBlank
    @Column(name = "date_registration")
    private LocalDateTime dataCadastro;

    // RELACIONAMENTOS
    // Uma área verde tem UM endereço
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Uma área verde pode ter MUITAS compensações
    @OneToMany(mappedBy = "greenArea", cascade = CascadeType.ALL)
    private List<Compensation> compensations = new ArrayList<>();

    @PrePersist
    protected void onCreate()
    {
        dataCadastro = LocalDateTime.now();
        if (areaAvailableM2 == null) {
            areaAvailableM2 = areaTotalM2;
        }
    }

    // Métod para verificar se há área disponível
    public Boolean temAreaDisponivel(Double areaRequerida)
    {
        return areaAvailableM2 >= areaRequerida;
    }

    // Métod para alocar área
    public void alocarArea(Double area)
    {
        this.areaAvailableM2 -= area;
    }
}
