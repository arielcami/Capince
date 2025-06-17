package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.capince.entity.TipoDocumentoEntity;

import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Long> {
    Optional<TipoDocumentoEntity> findByNombre(String nombre);
}
