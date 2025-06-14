package com.gabrielle_santiago.agenda.dto.request;

import java.time.LocalDate;

import com.gabrielle_santiago.agenda.authentication.UserRole;

public record PatientRegisterDTO(
    String username,
    String passwd, 
    String email,
    UserRole role,
    String name,
    int contact_number,
    String cpf,
    LocalDate dateBirth,
    String describe
) {}

