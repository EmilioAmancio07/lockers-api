package mx.cua.uam.lockersapi.service;

import mx.cua.uam.lockersapi.dto.LockerDTO;
import java.util.List;

public interface LockerService {
    List<LockerDTO> obtenerTodos();
    LockerDTO guardar(LockerDTO lockerDTO);
    List<LockerDTO> obtenerDisponibles();
    LockerDTO obtenerPorId(Integer id);
    LockerDTO actualizar(Integer id, LockerDTO lockerDTO);
}