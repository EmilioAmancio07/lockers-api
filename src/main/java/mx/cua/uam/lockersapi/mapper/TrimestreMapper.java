package mx.cua.uam.lockersapi.mapper;

import mx.cua.uam.lockersapi.dto.TrimestreDTO;
import mx.cua.uam.lockersapi.entity.Trimestre;

public class TrimestreMapper {
    public static TrimestreDTO toDTO(Trimestre entity) {
        TrimestreDTO dto = new TrimestreDTO();
        dto.setId(entity.getId());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        return dto;
    }

    public static Trimestre toEntity(TrimestreDTO dto) {
        Trimestre entity = new Trimestre();
        entity.setId(dto.getId());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        return entity;
    }
}