package pe.com.capince.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.capince.entity.RolEntity;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
	
	Optional<RolEntity> findByNombre(String nombre);
	
}
