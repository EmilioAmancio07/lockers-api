package mx.cua.uam.lockersapi.service;

import mx.cua.uam.lockersapi.dto.AlumnoDTO;
import java.util.List;

public interface AlumnoService {
    List<AlumnoDTO> obtenerTodos();
    AlumnoDTO guardar(AlumnoDTO alumnoDTO);
    AlumnoDTO obtenerPorId(Integer id); // Cambiado a Integer
}