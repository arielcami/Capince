package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.capince.entity.DeliveryEntity;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
	
	
}
