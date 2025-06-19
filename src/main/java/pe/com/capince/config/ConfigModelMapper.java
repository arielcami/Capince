package pe.com.capince.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.capince.dto.ClienteDTO;
import pe.com.capince.dto.PedidoDTO;
import pe.com.capince.entity.ClienteEntity;
import pe.com.capince.entity.DeliveryEntity;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.entity.PedidoEntity;

@Configuration
public class ConfigModelMapper {

	public static class EntityToStringConverter<T> extends AbstractConverter<T, String> {
		@Override
		protected String convert(T source) {
			if (source == null)
				return null;
			try {
				return (String) source.getClass().getMethod("getNombre").invoke(source);
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// ------------ ClienteEntity → ClienteDTO ------------
		modelMapper.typeMap(ClienteEntity.class, ClienteDTO.class).addMappings(mapper -> {
			mapper.map(src -> safeGet(src.getTipoDocumento()), ClienteDTO::setTipoDocumento);
			mapper.map(src -> safeGet(src.getSexo()), ClienteDTO::setSexo);
			mapper.map(src -> safeGet(src.getDistrito()), ClienteDTO::setDistrito);
		});

		// ------------ PedidoDTO → PedidoEntity ------------
		TypeMap<PedidoDTO, PedidoEntity> pedidoMap = modelMapper.emptyTypeMap(PedidoDTO.class, PedidoEntity.class);
		pedidoMap.addMappings(mapper -> {
			mapper.map(PedidoDTO::getId, PedidoEntity::setId);
			mapper.map(PedidoDTO::getTotal, PedidoEntity::setTotal);
			mapper.map(PedidoDTO::getEstado, PedidoEntity::setEstado);
			mapper.map(PedidoDTO::getDireccionEntrega, PedidoEntity::setDireccionEntrega);
			mapper.map(PedidoDTO::isLlevar, PedidoEntity::setLlevar);
			mapper.map(PedidoDTO::getFechaPedido, PedidoEntity::setFechaPedido);
			mapper.skip(PedidoEntity::setCliente);
			mapper.skip(PedidoEntity::setEmpleado);
			mapper.skip(PedidoEntity::setDelivery);
		});
		pedidoMap.setPostConverter(ctx -> {
			PedidoDTO dto = ctx.getSource();
			PedidoEntity entity = ctx.getDestination();

			if (dto.getClienteId() != null) {
				var cliente = new ClienteEntity();
				cliente.setId(dto.getClienteId().longValue());
				entity.setCliente(cliente);
			}
			if (dto.getEmpleadoId() != null) {
				var empleado = new EmpleadoEntity();
				empleado.setId(dto.getEmpleadoId().longValue());
				entity.setEmpleado(empleado);
			}
			if (dto.getDeliveryId() != null) {
				var delivery = new DeliveryEntity();
				delivery.setId(dto.getDeliveryId().longValue());
				entity.setDelivery(delivery);
			}
			return entity;
		});

		// ------------ PedidoEntity → PedidoDTO (seguro) ------------
		TypeMap<PedidoEntity, PedidoDTO> pedidoOut = modelMapper.emptyTypeMap(PedidoEntity.class, PedidoDTO.class);
		pedidoOut.addMappings(mapper -> {
			mapper.map(PedidoEntity::getId, PedidoDTO::setId);
			mapper.map(src -> safeId(src.getCliente()), PedidoDTO::setClienteId);
			mapper.map(src -> safeId(src.getEmpleado()), PedidoDTO::setEmpleadoId);
			mapper.map(src -> safeId(src.getDelivery()), PedidoDTO::setDeliveryId);
			mapper.map(PedidoEntity::getTotal, PedidoDTO::setTotal);
			mapper.map(PedidoEntity::getEstado, PedidoDTO::setEstado);
			mapper.map(PedidoEntity::getDireccionEntrega, PedidoDTO::setDireccionEntrega);
			mapper.map(PedidoEntity::isLlevar, PedidoDTO::setLlevar);
			mapper.map(PedidoEntity::getFechaPedido, PedidoDTO::setFechaPedido);
		});
		pedidoOut.setPostConverter(ctx -> {
			PedidoEntity entity = ctx.getSource();
			PedidoDTO dto = ctx.getDestination();

			dto.setClienteNombre(concatNombre(entity.getCliente()));
			dto.setEmpleadoNombre(concatNombre(entity.getEmpleado()));
			dto.setDeliveryNombre(concatNombre(entity.getDelivery()));

			return dto;
		});

		return modelMapper;
	}

	private Long safeId(Object obj) {
		try {
			if (obj == null)
				return null;
			return (Long) obj.getClass().getMethod("getId").invoke(obj);
		} catch (Exception e) {
			return null;
		}
	}

	private String safeGet(Object obj) {
		try {
			if (obj == null)
				return null;
			return (String) obj.getClass().getMethod("getNombre").invoke(obj);
		} catch (Exception e) {
			return null;
		}
	}

	private String concatNombre(Object persona) {
		if (persona == null)
			return null;
		try {
			String nombres = (String) persona.getClass().getMethod("getNombres").invoke(persona);
			String paterno = (String) persona.getClass().getMethod("getApellidoPaterno").invoke(persona);
			String materno = (String) persona.getClass().getMethod("getApellidoMaterno").invoke(persona);

			return (nombres + " " + paterno + " " + materno).trim();
		} catch (Exception e) {
			return null;
		}
	}

}
