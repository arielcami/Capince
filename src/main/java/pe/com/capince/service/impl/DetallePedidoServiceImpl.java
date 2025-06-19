package pe.com.capince.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pe.com.capince.dto.DetallePedidoDTO;
import pe.com.capince.entity.DetallePedidoEntity;
import pe.com.capince.entity.PedidoEntity;
import pe.com.capince.entity.ProductoEntity;
import pe.com.capince.repository.DetallePedidoRepository;
import pe.com.capince.repository.PedidoRepository;
import pe.com.capince.repository.ProductoRepository;
import pe.com.capince.service.DetallePedidoService;

@Service
@RequiredArgsConstructor
public class DetallePedidoServiceImpl implements DetallePedidoService {

	private final DetallePedidoRepository detallePedidoRepository;
	private final PedidoRepository pedidoRepository;
	private final ProductoRepository productoRepository;
	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public DetallePedidoDTO crear(DetallePedidoDTO dto) {
		PedidoEntity pedido = pedidoRepository.findById(dto.getPedidoId().longValue())
				.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

		ProductoEntity producto = productoRepository.findById(dto.getProductoId().longValue())
				.orElseThrow(() -> new RuntimeException("Producto no encontrado"));

		DetallePedidoEntity entity = modelMapper.map(dto, DetallePedidoEntity.class);
		entity.setPedido(pedido);
		entity.setProducto(producto);
		entity.setEstado(true); // Estado activo por defecto

		return modelMapper.map(detallePedidoRepository.save(entity), DetallePedidoDTO.class);
	}

	@Override
	@Transactional
	public DetallePedidoDTO actualizar(Long id, DetallePedidoDTO dto) {
		DetallePedidoEntity existente = detallePedidoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));

		existente.setCantidad(dto.getCantidad());
		existente.setPrecioUnitario(dto.getPrecioUnitario());
		existente.setComentario(dto.getComentario());

		// No se permite cambiar el pedido o el producto en una actualización

		return modelMapper.map(detallePedidoRepository.save(existente), DetallePedidoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public DetallePedidoDTO obtenerPorId(Long id) {
		DetallePedidoEntity entity = detallePedidoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
		return modelMapper.map(entity, DetallePedidoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetallePedidoDTO> listarPorPedidoId(Long pedidoId) {
		return detallePedidoRepository.findAll().stream()
				.filter(d -> d.getPedido().getId().equals(pedidoId) && d.isEstado())
				.map(d -> modelMapper.map(d, DetallePedidoDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		DetallePedidoEntity entity = detallePedidoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
		entity.setEstado(false); // borrado lógico
		detallePedidoRepository.save(entity);
	}

	@Override
	@Transactional
	public void cambiarEstado(Long pedidoId, Byte nuevoEstado) {
		PedidoEntity pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

		pedido.setEstado(nuevoEstado);
		pedidoRepository.save(pedido);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetallePedidoDTO> listarTodos() {
	    return detallePedidoRepository.findAll()
	        .stream()
	        .map(detalle -> modelMapper.map(detalle, DetallePedidoDTO.class))
	        .toList();
	}
}
