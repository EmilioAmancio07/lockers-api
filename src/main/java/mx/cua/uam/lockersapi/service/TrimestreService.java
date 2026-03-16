package mx.cua.uam.lockersapi.service;

import mx.cua.uam.lockersapi.dto.TrimestreDTO;
import java.util.List;

public interface TrimestreService {
    List<TrimestreDTO> obtenerTodos();
    TrimestreDTO guardar(TrimestreDTO trimestreDTO);
    TrimestreDTO obtenerPorId(String id);
}