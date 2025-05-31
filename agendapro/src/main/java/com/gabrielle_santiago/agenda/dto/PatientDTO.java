package com.gabrielle_santiago.agenda.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class PatientDTO extends PeopleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String passwd;

    public PatientDTO(String name, int number, String cpf, Date dateBrith, String email, String username, String passwd) {
        super(name, number, cpf, dateBrith, email);
        this.username = username;
        this.passwd = passwd;
    }

}
