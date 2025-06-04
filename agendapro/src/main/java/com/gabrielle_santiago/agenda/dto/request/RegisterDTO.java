package com.gabrielle_santiago.agenda.dto.request;

import java.util.Date;

import com.gabrielle_santiago.agenda.authentication.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String username, @NotBlank String passwd, @NotBlank @Email String email, UserRole role, @NotBlank String name, int contact_number, String cpf, Date dateBirth) {}
