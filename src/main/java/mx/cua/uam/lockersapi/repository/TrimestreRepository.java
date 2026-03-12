package mx.cua.uam.lockersapi.repository;

import mx.cua.uam.lockersapi.entity.Trimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimestreRepository extends JpaRepository<Trimestre, String> {
}