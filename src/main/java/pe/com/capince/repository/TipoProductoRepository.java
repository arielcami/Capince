package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.capince.entity.TipoProductoEntity;

import java.util.Optional;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long> {
    
    // Para buscar por nombre exacto (si lo necesitas en el futuro)
    Optional<TipoProductoEntity> findByNombre(String nombre);

    // Para verificar si existe un nombre antes de insertar
    boolean existsByNombre(String nombre);
}
