package mx.cua.uam.lockersapi.repository;

import mx.cua.uam.lockersapi.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> { // Integer aquí
}