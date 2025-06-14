package com.gabrielle_santiago.agenda.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gabrielle_santiago.agenda.dto.request.EmployeeRegisterDTO;
import com.gabrielle_santiago.agenda.entity.EmployeeEntity;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(com.gabrielle_santiago.agenda.authentication.UserRole.EMPLOYEE)")
    @Mapping(target = "passwd", expression = "java(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(dto.passwd()))")
    EmployeeEntity toEntity(EmployeeRegisterDTO dto);
}
