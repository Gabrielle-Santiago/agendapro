package com.gabrielle_santiago.agenda.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
public class PeopleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
