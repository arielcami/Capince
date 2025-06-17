package pe.com.capince.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.capince.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
	
    List<ClienteEntity> findByDocumentoContaining(String documentoFragment);
    
    @Modifying
    @Query("UPDATE ClienteEntity c SET c.estado = false WHERE c.id = :id")
    void desactivar(@Param("id") Long id);

    @Modifying
    @Query("UPDATE ClienteEntity c SET c.estado = true WHERE c.id = :id")
    void activar(@Param("id") Long id);
    
}