package com.gabrielle_santiago.agenda.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gabrielle_santiago.agenda.dto.record.PatientRegisterDTO;
import com.gabrielle_santiago.agenda.entity.PatientEntity;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(com.gabrielle_santiago.agenda.authentication.UserRole.PATIENT)")
    @Mapping(target = "passwd", expression = "java(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(dto.passwd()))")
    PatientEntity toEntity(PatientRegisterDTO dto);
}
