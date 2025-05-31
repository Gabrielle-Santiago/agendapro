package com.gabrielle_santiago.agenda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@DiscriminatorValue("employees")
public class EmployeeEntity extends PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String role;
    private String proof;
    private String username;
    private String passwd;

    public EmployeeEntity(){}

    public EmployeeEntity(String name, int number, String cpf, Date dateBrith, String email, String role, String proof, String username, String passwd) {
        super(name, number, cpf, dateBrith, email);
        this.role = role;
        this.proof = proof;
        this.username = username;
        this.passwd = passwd;
    }

}
