package com.gabrielle_santiago.agenda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@DiscriminatorValue("patients")
public class PatientEntity extends PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String username;
    private String passwd;

    public PatientEntity(){}

    public PatientEntity(String name, int number, String cpf, Date dateBrith, String email, String username, String passwd) {
        super(name, number, cpf, dateBrith, email);
        this.username = username;
        this.passwd = passwd;
    }

}
