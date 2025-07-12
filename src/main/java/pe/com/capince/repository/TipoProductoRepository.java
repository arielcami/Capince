package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.capince.entity.TipoProductoEntity;
import java.util.Optional;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long> {
    
    Optional<TipoProductoEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
