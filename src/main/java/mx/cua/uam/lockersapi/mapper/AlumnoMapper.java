package mx.cua.uam.lockersapi.mapper;

import mx.cua.uam.lockersapi.dto.AlumnoDTO;
import mx.cua.uam.lockersapi.entity.Alumno;

public class AlumnoMapper {
    public static AlumnoDTO toDTO(Alumno alumno) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setId(alumno.getId());
        dto.setNombre(alumno.getNombre());
        dto.setCorreo(alumno.getCorreo());
        dto.setLicenciatura(alumno.getLicenciatura());
        dto.setEstatus(alumno.getEstatus().name());
        return dto;
    }

    public static Alumno toEntity(AlumnoDTO dto) {
        Alumno alumno = new Alumno();
        alumno.setId(dto.getId());
        alumno.setNombre(dto.getNombre());
        alumno.setCorreo(dto.getCorreo());
        alumno.setLicenciatura(dto.getLicenciatura());
        alumno.setEstatus(Alumno.EstatusAlumno.valueOf(dto.getEstatus()));
        return alumno;
    }
}