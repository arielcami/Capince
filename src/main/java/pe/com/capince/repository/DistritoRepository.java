package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.capince.entity.DistritoEntity;

import java.util.Optional;

public interface DistritoRepository extends JpaRepository<DistritoEntity, Long> {
    Optional<DistritoEntity> findByNombre(String nombre);

}
