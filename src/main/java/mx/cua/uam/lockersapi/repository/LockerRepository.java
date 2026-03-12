package mx.cua.uam.lockersapi.repository;

import mx.cua.uam.lockersapi.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Integer> { // Integer aquí
}