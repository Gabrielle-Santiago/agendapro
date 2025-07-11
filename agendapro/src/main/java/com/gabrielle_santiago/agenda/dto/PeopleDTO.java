package com.gabrielle_santiago.agenda.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PeopleDTO {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String number;
    private String cpf;
    private LocalDate dateBrith;
    private String email;

    public PeopleDTO(String name, String number, String cpf, LocalDate dateBrith, String email) {
        this.name = name;
        this.number = number;
        this.cpf = cpf;
        this.dateBrith = dateBrith;
        this.email = email;
    }

}
