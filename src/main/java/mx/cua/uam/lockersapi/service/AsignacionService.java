package mx.cua.uam.lockersapi.service;

import mx.cua.uam.lockersapi.dto.AsignacionDTO;
import java.util.List;

public interface AsignacionService {
    List<AsignacionDTO> obtenerTodas();
    AsignacionDTO crearAsignacion(AsignacionDTO asignacionDTO);
    AsignacionDTO devolverLocker(Integer idAsignacion);
}