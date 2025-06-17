package pe.com.capince.service;

import pe.com.capince.dto.ProductoDTO;
import pe.com.capince.entity.ProductoEntity;
import pe.com.capince.service.generic.ServiceGeneric;

import java.util.List;

public interface ProductoService extends ServiceGeneric<ProductoEntity, Long> {

    ProductoDTO saveDTO(ProductoDTO dto);

    ProductoDTO findByIdDTO(Long id);

    List<ProductoDTO> findAllDTO();

    ProductoDTO updateDTO(Long id, ProductoDTO dto);

    void deleteByIdDTO(Long id);

    void cambiarEstado(Long id, boolean estado);

    void ajustarStock(Long id, int cantidad);
}
