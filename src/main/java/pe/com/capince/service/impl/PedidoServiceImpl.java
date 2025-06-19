package pe.com.capince.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pe.com.capince.dto.PedidoDTO;
import pe.com.capince.entity.ClienteEntity;
import pe.com.capince.entity.DeliveryEntity;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.entity.PedidoEntity;
import pe.com.capince.repository.ClienteRepository;
import pe.com.capince.repository.DeliveryRepository;
import pe.com.capince.repository.EmpleadoRepository;
import pe.com.capince.repository.PedidoRepository;
import pe.com.capince.service.PedidoService;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final EmpleadoRepository empleadoRepository;
	private final DeliveryRepository deliveryRepository;
	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public PedidoDTO crear(PedidoDTO dto) {
		ClienteEntity cliente = clienteRepository.findById(dto.getClienteId().longValue())
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

		EmpleadoEntity empleado = null;
		if (dto.getEmpleadoId() != null) {
			empleado = empleadoRepository.findById(dto.getEmpleadoId().longValue())
					.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
		}

		DeliveryEntity delivery = null;
		if (dto.getDeliveryId() != null) {
			delivery = deliveryRepository.findById(dto.getDeliveryId().longValue())
					.orElseThrow(() -> new RuntimeException("Delivery no encontrado"));
		}

		PedidoEntity entity = modelMapper.map(dto, PedidoEntity.class);
		entity.setCliente(cliente);
		entity.setEmpleado(empleado);
		entity.setDelivery(delivery);
		entity.setEstado(dto.getEstado() != null ? dto.getEstado() : (byte) 0); // Estado inicial si no se pasa

		return modelMapper.map(pedidoRepository.save(entity), PedidoDTO.class);
	}

	@Override
	@Transactional
	public PedidoDTO actualizar(Long id, PedidoDTO dto) {
		PedidoEntity existente = pedidoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

		if (dto.getEmpleadoId() != null) {
			EmpleadoEntity empleado = empleadoRepository.findById(dto.getEmpleadoId().longValue())
					.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
			existente.setEmpleado(empleado);
		}

		if (dto.getDeliveryId() != null) {
			DeliveryEntity delivery = deliveryRepository.findById(dto.getDeliveryId().longValue())
					.orElseThrow(() -> new RuntimeException("Delivery no encontrado"));
			existente.setDelivery(delivery);
		}

		if (dto.getDireccionEntrega() != null)
			existente.setDireccionEntrega(dto.getDireccionEntrega());

		if (dto.getEstado() != null)
			existente.setEstado(dto.getEstado());

		if (dto.getTotal() != null)
			existente.setTotal(dto.getTotal());

		existente.setLlevar(dto.isLlevar());

		return modelMapper.map(pedidoRepository.save(existente), PedidoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public PedidoDTO obtenerPorId(Long id) {
		PedidoEntity entity = pedidoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
		return modelMapper.map(entity, PedidoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PedidoDTO> listarTodos() {
		return pedidoRepository.findAll().stream().filter(p -> p.getEstado() != 0) // omitimos pedidos eliminados
				.map(p -> modelMapper.map(p, PedidoDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		PedidoEntity entity = pedidoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
		entity.setEstado((byte) 0); // borrado lógico
		pedidoRepository.save(entity);
	}

	@Override
	@Transactional
	public void cambiarEstado(Long pedidoId, Byte nuevoEstado) {
		PedidoEntity pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

		pedido.setEstado(nuevoEstado);
		pedidoRepository.save(pedido);
	}
}
