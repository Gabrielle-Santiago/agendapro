package com.gabrielle_santiago.agenda.mapper;

import com.gabrielle_santiago.agenda.dto.request.ConsultationDTO;
import com.gabrielle_santiago.agenda.entity.ConsultationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    @Mapping(target = "id", ignore = true)
    ConsultationEntity toEntity(ConsultationDTO dto);
}
