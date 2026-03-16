package mx.cua.uam.lockersapi.mapper;

import mx.cua.uam.lockersapi.dto.AsignacionDTO;
import mx.cua.uam.lockersapi.entity.Alumno;
import mx.cua.uam.lockersapi.entity.Asignacion;
import mx.cua.uam.lockersapi.entity.Locker;
import mx.cua.uam.lockersapi.entity.Trimestre;

public class AsignacionMapper {

    public static AsignacionDTO toDTO(Asignacion entity) {
        AsignacionDTO dto = new AsignacionDTO();
        dto.setId(entity.getId());
        dto.setIdAlumno(entity.getAlumno().getId());
        dto.setIdLocker(entity.getLocker().getId());
        dto.setIdTrimestre(entity.getTrimestre().getId());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setEstatusEntrega(entity.getEstatusEntrega().name());
        return dto;
    }

    public static Asignacion toEntity(AsignacionDTO dto, Alumno alumno, Locker locker, Trimestre trimestre) {
        Asignacion entity = new Asignacion();
        entity.setId(dto.getId());
        entity.setAlumno(alumno);
        entity.setLocker(locker);
        entity.setTrimestre(trimestre);

        entity.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : java.time.LocalDateTime.now());

        entity.setEstatusEntrega(dto.getEstatusEntrega() != null ?
                Asignacion.EstatusEntrega.valueOf(dto.getEstatusEntrega()) : Asignacion.EstatusEntrega.Activo);

        return entity;
    }
}