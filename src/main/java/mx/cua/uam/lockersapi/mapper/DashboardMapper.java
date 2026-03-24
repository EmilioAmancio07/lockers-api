package mx.cua.uam.lockersapi.mapper;

import mx.cua.uam.lockersapi.dto.DashboardDTO;
import mx.cua.uam.lockersapi.entity.Alumno;
import org.springframework.stereotype.Component;

@Component
public class DashboardMapper {

    public DashboardDTO toDTO(Alumno alumno) {
        DashboardDTO dto = new DashboardDTO();
        dto.setNombreAlumno(alumno.getNombre());
        dto.setMatricula(alumno.getMatricula() != null ? alumno.getMatricula() : "Sin Matrícula");
        return dto;
    }
}