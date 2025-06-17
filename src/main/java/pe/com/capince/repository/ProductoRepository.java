package pe.com.capince.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.com.capince.entity.ProductoEntity;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    boolean existsByNombre(String nombre);
    
	@Modifying
	@Transactional
	@Query("UPDATE ProductoEntity p SET p.estado = :estado WHERE p.id = :id")
	void updateEstadoById(Long id, boolean estado);
	
	@Modifying
	@Transactional
	@Query("UPDATE ProductoEntity p SET p.stock = p.stock + :cantidad WHERE p.id = :id")
	void actualizarStock(Long id, int cantidad);
    
    
}