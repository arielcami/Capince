package pe.com.capince.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import pe.com.capince.dto.ProductoDTO;
import pe.com.capince.dto.TipoProductoDTO;
import pe.com.capince.entity.ProductoEntity;
import pe.com.capince.entity.TipoProductoEntity;
import pe.com.capince.repository.ProductoRepository;
import pe.com.capince.repository.TipoProductoRepository;
import pe.com.capince.service.ProductoService;
import pe.com.capince.service.generic.ServiceGenericImpl;

@Service
public class ProductoServiceImpl extends ServiceGenericImpl<ProductoEntity, Long> implements ProductoService {

    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, TipoProductoRepository tipoProductoRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    @Override
    public ProductoDTO saveDTO(ProductoDTO dto) {
        ProductoEntity entity = toEntity(dto);
        ProductoEntity guardado = productoRepository.save(entity);
        return toDTO(guardado);
    }

    @Override
    public ProductoDTO findByIdDTO(Long id) {
        return productoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Override
    public List<ProductoDTO> findAllDTO() {
        return productoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ProductoDTO updateDTO(Long id, ProductoDTO dto) {
        ProductoEntity existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        existente.setNombre(dto.getNombre());
        existente.setPrecio(BigDecimal.valueOf(dto.getPrecio()));
        existente.setStock(dto.getStock());
        existente.setEstado(dto.isEstado());

        TipoProductoEntity tipoProducto = tipoProductoRepository.findById(dto.getTipoProducto().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de producto no válido"));
        existente.setTipoProducto(tipoProducto);

        ProductoEntity actualizado = productoRepository.save(existente);
        return toDTO(actualizado);
    }

    @Override
    public void deleteByIdDTO(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public void cambiarEstado(Long id, boolean estado) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.updateEstadoById(id, estado);
    }

    @Override
    public void ajustarStock(Long id, int cantidad) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.actualizarStock(id, cantidad);
    }

    // 🔁 Conversión Entity -> DTO
    private ProductoDTO toDTO(ProductoEntity entity) {
        return new ProductoDTO(
                entity.getId(),
                entity.getNombre(),
                new TipoProductoDTO(entity.getTipoProducto().getId(), entity.getTipoProducto().getNombre()),
                entity.getPrecio().doubleValue(),
                entity.getStock(),
                entity.isEstado()
        );
    }

    // 🔁 Conversión DTO -> Entity
    private ProductoEntity toEntity(ProductoDTO dto) {
        TipoProductoEntity tipoProducto = tipoProductoRepository.findById(dto.getTipoProducto().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de producto no válido"));

        return ProductoEntity.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .precio(BigDecimal.valueOf(dto.getPrecio()))
                .stock(dto.getStock())
                .estado(dto.isEstado())
                .tipoProducto(tipoProducto)
                .build();
    }
}
