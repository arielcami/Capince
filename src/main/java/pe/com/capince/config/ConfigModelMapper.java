package pe.com.capince.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.capince.dto.ClienteDTO;
import pe.com.capince.dto.DetallePedidoDTO;
import pe.com.capince.dto.PedidoDTO;
import pe.com.capince.entity.ClienteEntity;
import pe.com.capince.entity.DeliveryEntity;
import pe.com.capince.entity.DetallePedidoEntity;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.entity.PedidoEntity;
import pe.com.capince.entity.ProductoEntity;

@Configuration
public class ConfigModelMapper {

	public static class EntityToStringConverter<T> extends AbstractConverter<T, String> {
		@Override
		protected String convert(T source) {
			if (source == null) return null;
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

		// Conversor genérico para entidades con método getNombre()
		Converter<Object, String> entityToStringConverter = new EntityToStringConverter<>();
		modelMapper.addConverter(entityToStringConverter);

		// ------------ ClienteEntity → ClienteDTO ------------
		modelMapper.typeMap(ClienteEntity.class, ClienteDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getTipoDocumento().getNombre(), ClienteDTO::setTipoDocumento);
			mapper.map(src -> src.getSexo().getNombre(), ClienteDTO::setSexo);
			mapper.map(src -> src.getDistrito().getNombre(), ClienteDTO::setDistrito);
		});

		// ------------ PedidoDTO → PedidoEntity (sin conflictos) ------------
		TypeMap<PedidoDTO, PedidoEntity> pedidoMap = modelMapper.emptyTypeMap(PedidoDTO.class, PedidoEntity.class);

		pedidoMap.addMappings(mapper -> {
			mapper.map(PedidoDTO::getId, PedidoEntity::setId);
			mapper.map(PedidoDTO::getTotal, PedidoEntity::setTotal);
			mapper.map(PedidoDTO::getEstado, PedidoEntity::setEstado);
			mapper.map(PedidoDTO::getDireccionEntrega, PedidoEntity::setDireccionEntrega);
			mapper.map(PedidoDTO::isLlevar, PedidoEntity::setLlevar);
			mapper.map(PedidoDTO::getFechaPedido, PedidoEntity::setFechaPedido);
			// Relaciones se manejan manualmente abajo
			mapper.skip(PedidoEntity::setCliente);
			mapper.skip(PedidoEntity::setEmpleado);
			mapper.skip(PedidoEntity::setDelivery);
		});

		pedidoMap.setPostConverter(ctx -> {
			PedidoDTO dto = ctx.getSource();
			PedidoEntity entity = ctx.getDestination();

			if (dto.getClienteId() != null) {
				ClienteEntity cliente = new ClienteEntity();
				cliente.setId(dto.getClienteId().longValue());
				entity.setCliente(cliente);
			}
			if (dto.getEmpleadoId() != null) {
				EmpleadoEntity empleado = new EmpleadoEntity();
				empleado.setId(dto.getEmpleadoId().longValue());
				entity.setEmpleado(empleado);
			}
			if (dto.getDeliveryId() != null) {
				DeliveryEntity delivery = new DeliveryEntity();
				delivery.setId(dto.getDeliveryId().longValue());
				entity.setDelivery(delivery);
			}
			return entity;
		});

		// ------------ DetallePedidoDTO → DetallePedidoEntity (seguro) ------------
		TypeMap<DetallePedidoDTO, DetallePedidoEntity> detalleMap =
				modelMapper.emptyTypeMap(DetallePedidoDTO.class, DetallePedidoEntity.class);

		detalleMap.addMappings(mapper -> {
			mapper.map(DetallePedidoDTO::getId, DetallePedidoEntity::setId);
			mapper.map(DetallePedidoDTO::getCantidad, DetallePedidoEntity::setCantidad);
			mapper.map(DetallePedidoDTO::getPrecioUnitario, DetallePedidoEntity::setPrecioUnitario);
			mapper.skip(DetallePedidoEntity::setPedido);
			mapper.skip(DetallePedidoEntity::setProducto);
		});

		detalleMap.setPostConverter(ctx -> {
			DetallePedidoDTO dto = ctx.getSource();
			DetallePedidoEntity entity = ctx.getDestination();

			if (dto.getPedidoId() != null) {
				PedidoEntity pedido = new PedidoEntity();
				pedido.setId(dto.getPedidoId().longValue());
				entity.setPedido(pedido);
			}
			if (dto.getProductoId() != null) {
				ProductoEntity producto = new ProductoEntity();
				producto.setId(dto.getProductoId().longValue());
				entity.setProducto(producto);
			}
			return entity;
		});

		return modelMapper;
	}
}
