package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.capince.entity.DetallePedidoEntity;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long> {
	
	
}
