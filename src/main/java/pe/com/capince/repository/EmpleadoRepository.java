package pe.com.capince.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.capince.entity.EmpleadoEntity;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    Optional<EmpleadoEntity> findByDocumento(String documento);
    
    @Modifying
    @Query("UPDATE EmpleadoEntity e SET e.estado = false WHERE e.id = :id")
    void desactivar(@Param("id") Long id);

    @Modifying
    @Query("UPDATE EmpleadoEntity e SET e.estado = true WHERE e.id = :id")
    void activar(@Param("id") Long id);
    
}
