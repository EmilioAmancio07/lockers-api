package mx.cua.uam.lockersapi.service.impl;

import mx.cua.uam.lockersapi.dto.LockerDTO;
import mx.cua.uam.lockersapi.entity.Locker;
import mx.cua.uam.lockersapi.mapper.LockerMapper;
import mx.cua.uam.lockersapi.repository.LockerRepository;
import mx.cua.uam.lockersapi.service.LockerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LockerServiceImpl implements LockerService {

    private final LockerRepository lockerRepository;

    public LockerServiceImpl(LockerRepository lockerRepository) {
        this.lockerRepository = lockerRepository;
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
}