package mx.cua.uam.lockersapi.repository;

import mx.cua.uam.lockersapi.entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Integer> {
    boolean existsByAlumnoIdAndTrimestreId(Integer alumnoId, String trimestreId);

    Optional<Asignacion> findByAlumnoIdAndEstatusEntrega(Integer alumnoId, Asignacion.EstatusEntrega estatus);
    Optional<Asignacion> findByLockerIdAndEstatusEntrega(Integer lockerId, Asignacion.EstatusEntrega estatus);
}