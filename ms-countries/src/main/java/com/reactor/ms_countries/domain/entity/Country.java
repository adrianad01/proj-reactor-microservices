package com.reactor.ms_countries.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paises", schema = "dbo")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Integer idCountry;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "codigo")
    private String code;
}
