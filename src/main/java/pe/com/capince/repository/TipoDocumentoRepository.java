package pe.com.capince.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.capince.entity.TipoDocumentoEntity;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Long> {
	Optional<TipoDocumentoEntity> findByNombre(String nombre);
}
