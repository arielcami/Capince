package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.capince.entity.SexoEntity;

import java.util.Optional;

public interface SexoRepository extends JpaRepository<SexoEntity, Short> {
    Optional<SexoEntity> findByNombre(String nombre);
}
