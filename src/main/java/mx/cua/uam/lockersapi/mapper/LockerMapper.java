package mx.cua.uam.lockersapi.mapper;

import mx.cua.uam.lockersapi.dto.LockerDTO;
import mx.cua.uam.lockersapi.entity.Locker;

public class LockerMapper {
    public static LockerDTO toDTO(Locker entity) {
        LockerDTO dto = new LockerDTO();
        dto.setId(entity.getId());
        dto.setNumeroLocker(entity.getNumeroLocker());
        dto.setUbicacion(entity.getUbicacion());
        dto.setEstado(entity.getEstado().name());
        dto.setDimensiones(entity.getDimensiones().name());
        return dto;
    }

    public static Locker toEntity(LockerDTO dto) {
        Locker entity = new Locker();
        entity.setId(dto.getId());
        entity.setNumeroLocker(dto.getNumeroLocker());
        entity.setUbicacion(dto.getUbicacion());
        entity.setEstado(Locker.EstadoLocker.valueOf(dto.getEstado()));
        entity.setDimensiones(Locker.Dimensiones.valueOf(dto.getDimensiones()));
        return entity;
    }
}