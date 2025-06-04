package com.gabrielle_santiago.agenda.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gabrielle_santiago.agenda.authentication.UserRole;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "people")
public abstract class PeopleEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String passwd;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String name;
    private int contact_number;
    private String cpf;
    private LocalDate dateBirth;  

    public PeopleEntity(){}

    public PeopleEntity(String name, String username, String passwd, String email, int contact_number, String cpf, LocalDate dateBirth, UserRole role) {
        this.name = name;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.contact_number = contact_number;
        this.cpf = cpf;
        this.dateBirth = dateBirth;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.PATIENT) return List.of(new SimpleGrantedAuthority("patient"), new SimpleGrantedAuthority("employee"));
        else return List.of(new SimpleGrantedAuthority("employee"));
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
