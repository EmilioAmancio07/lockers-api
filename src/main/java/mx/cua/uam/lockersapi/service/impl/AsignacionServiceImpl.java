package mx.cua.uam.lockersapi.service.impl;

import mx.cua.uam.lockersapi.dto.AsignacionDTO;
import mx.cua.uam.lockersapi.entity.Alumno;
import mx.cua.uam.lockersapi.entity.Asignacion;
import mx.cua.uam.lockersapi.entity.Locker;
import mx.cua.uam.lockersapi.entity.Trimestre;
import mx.cua.uam.lockersapi.mapper.AsignacionMapper;
import mx.cua.uam.lockersapi.repository.AlumnoRepository;
import mx.cua.uam.lockersapi.repository.AsignacionRepository;
import mx.cua.uam.lockersapi.repository.LockerRepository;
import mx.cua.uam.lockersapi.repository.TrimestreRepository;
import mx.cua.uam.lockersapi.service.AsignacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    private final AsignacionRepository asignacionRepository;
    private final AlumnoRepository alumnoRepository;
    private final LockerRepository lockerRepository;
    private final TrimestreRepository trimestreRepository;

    public AsignacionServiceImpl(AsignacionRepository asignacionRepository,
                                 AlumnoRepository alumnoRepository,
                                 LockerRepository lockerRepository,
                                 TrimestreRepository trimestreRepository) {
        this.asignacionRepository = asignacionRepository;
        this.alumnoRepository = alumnoRepository;
        this.lockerRepository = lockerRepository;
        this.trimestreRepository = trimestreRepository;
    }

    @Override
    public List<AsignacionDTO> obtenerTodas() {
        return asignacionRepository.findAll().stream()
                .map(AsignacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AsignacionDTO crearAsignacion(AsignacionDTO dto) {

        Alumno alumno = alumnoRepository.findById(dto.getIdAlumno())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        if (alumno.getEstatus() == Alumno.EstatusAlumno.Baja) {
            throw new RuntimeException("El alumno está dado de baja y no puede solicitar lockers.");
        }

        Trimestre trimestre = trimestreRepository.findById(dto.getIdTrimestre())
                .orElseThrow(() -> new RuntimeException("Trimestre no encontrado"));

        if (asignacionRepository.existsByAlumnoIdAndTrimestreId(alumno.getId(), trimestre.getId())) {
            throw new RuntimeException("El alumno ya tiene un locker asignado en este trimestre.");
        }

        Locker locker = lockerRepository.findById(dto.getIdLocker())
                .orElseThrow(() -> new RuntimeException("Locker no encontrado"));
        if (locker.getEstado() != Locker.EstadoLocker.Disponible) {
            throw new RuntimeException("El locker seleccionado no está disponible.");
        }

        Asignacion nuevaAsignacion = AsignacionMapper.toEntity(dto, alumno, locker, trimestre);
        Asignacion guardada = asignacionRepository.save(nuevaAsignacion);

        locker.setEstado(Locker.EstadoLocker.Ocupado);
        lockerRepository.save(locker);

        return AsignacionMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public AsignacionDTO devolverLocker(Integer idAsignacion) {
        Asignacion asignacion = asignacionRepository.findById(idAsignacion)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        if (asignacion.getEstatusEntrega() == Asignacion.EstatusEntrega.Entregado) {
            throw new RuntimeException("Este locker ya fue devuelto previamente.");
        }

        asignacion.setEstatusEntrega(Asignacion.EstatusEntrega.Entregado);
        Asignacion guardada = asignacionRepository.save(asignacion);

        Locker locker = asignacion.getLocker();
        locker.setEstado(Locker.EstadoLocker.Disponible);
        lockerRepository.save(locker);

        return AsignacionMapper.toDTO(guardada);
    }
}