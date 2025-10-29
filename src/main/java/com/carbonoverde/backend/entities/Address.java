package com.carbonoverde.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @NotBlank
    @Column(name = "country", length = 100)
    private String country;

    @NotBlank
    @Column(name = "state", length = 100)
    private String state;

    @NotBlank
    @Column(name = "city", length = 100)
    private String city;

    @NotBlank
    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @NotBlank
    @Column(name = "street")
    private String street;

    @Column(name = "number", length = 10, nullable = false)
    private String number;

    @Column(name = "complement", length = 100)
    private String complement;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
