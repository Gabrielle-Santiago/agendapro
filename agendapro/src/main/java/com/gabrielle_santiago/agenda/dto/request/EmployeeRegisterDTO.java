package com.gabrielle_santiago.agenda.dto.request;

import java.time.LocalDate;

import com.gabrielle_santiago.agenda.authentication.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record EmployeeRegisterDTO(
    String username,
    String passwd,
    @Email(message = "Email must be a valid email address.")
    String email,
    UserRole role,
    String name,
    String contact_number,

    @Pattern(regexp = "^\\d{11}$", message = "The CPF must contain exactly 11 numeric digits.")
    String cpf,

    LocalDate dateBirth,
    String proof
) {}
