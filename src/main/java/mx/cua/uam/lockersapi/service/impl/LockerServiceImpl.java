package mx.cua.uam.lockersapi.service.impl;

import mx.cua.uam.lockersapi.dto.LockerDTO;
import mx.cua.uam.lockersapi.entity.Asignacion;
import mx.cua.uam.lockersapi.entity.Locker;
import mx.cua.uam.lockersapi.mapper.LockerMapper;
import mx.cua.uam.lockersapi.repository.AsignacionRepository;
import mx.cua.uam.lockersapi.repository.LockerRepository;
import mx.cua.uam.lockersapi.service.LockerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LockerServiceImpl implements LockerService {

    private final LockerRepository lockerRepository;
    private final AsignacionRepository asignacionRepository;

    public LockerServiceImpl(LockerRepository lockerRepository, AsignacionRepository asignacionRepository) {
        this.lockerRepository = lockerRepository;
        this.asignacionRepository = asignacionRepository;
    }

    @Override
    public List<LockerDTO> obtenerTodos() {
        return lockerRepository.findAll().stream()
                .map(LockerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LockerDTO guardar(LockerDTO lockerDTO) {
        Locker lockerEntity = LockerMapper.toEntity(lockerDTO);
        Locker lockerGuardado = lockerRepository.save(lockerEntity);
        return LockerMapper.toDTO(lockerGuardado);
    }

    @Override
    public List<LockerDTO> obtenerDisponibles() {
        // Filtramos para devolver SOLO los que tienen estado 'Disponible'
        return lockerRepository.findAll().stream()
                .filter(locker -> locker.getEstado() == Locker.EstadoLocker.Disponible)
                .map(LockerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LockerDTO obtenerPorId(Integer id) {
        Locker locker = lockerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locker no encontrado con ID: " + id));

        LockerDTO dto = LockerMapper.toDTO(locker);

        // Si está ocupado, buscamos qué alumno lo tiene
        if (locker.getEstado() == Locker.EstadoLocker.Ocupado) {
            asignacionRepository.findByLockerIdAndEstatusEntrega(id, Asignacion.EstatusEntrega.Activo)
                    .ifPresent(asignacion -> dto.setAlumnoAsignado(asignacion.getAlumno().getNombre()));
        }
        return dto;
    }

    @Override
    public LockerDTO actualizar(Integer id, LockerDTO lockerDTO) {
        Locker locker = lockerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locker no encontrado con ID: " + id));

        if (lockerDTO.getNumeroLocker() != null) locker.setNumeroLocker(lockerDTO.getNumeroLocker());
        if (lockerDTO.getUbicacion() != null) locker.setUbicacion(lockerDTO.getUbicacion());
        if (lockerDTO.getEstado() != null) locker.setEstado(Locker.EstadoLocker.valueOf(lockerDTO.getEstado()));
        if (lockerDTO.getDimensiones() != null) locker.setDimensiones(Locker.Dimensiones.valueOf(lockerDTO.getDimensiones()));

        Locker lockerGuardado = lockerRepository.save(locker);
        return LockerMapper.toDTO(lockerGuardado);
    }
}