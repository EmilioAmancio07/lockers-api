package mx.cua.uam.lockersapi.service.impl;

import mx.cua.uam.lockersapi.dto.AlumnoDTO;
import mx.cua.uam.lockersapi.entity.Alumno;
import mx.cua.uam.lockersapi.mapper.AlumnoMapper;
import mx.cua.uam.lockersapi.repository.AlumnoRepository;
import mx.cua.uam.lockersapi.service.AlumnoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public List<AlumnoDTO> obtenerTodos() {
        return alumnoRepository.findAll().stream()
                .map(AlumnoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDTO guardar(AlumnoDTO alumnoDTO) {
        Alumno alumnoEntity = AlumnoMapper.toEntity(alumnoDTO);
        Alumno alumnoGuardado = alumnoRepository.save(alumnoEntity);
        return AlumnoMapper.toDTO(alumnoGuardado);
    }

    @Override
    public AlumnoDTO obtenerPorId(Integer id) { // Cambiado a Integer
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con ID: " + id));
        return AlumnoMapper.toDTO(alumno);
    }
}