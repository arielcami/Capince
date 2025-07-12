package pe.com.capince.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pe.com.capince.dto.TipoProductoDTO;
import pe.com.capince.entity.TipoProductoEntity;
import pe.com.capince.repository.TipoProductoRepository;
import pe.com.capince.service.TipoProductoService;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

	private final TipoProductoRepository tipoProductoRepository;

	public TipoProductoServiceImpl(TipoProductoRepository tipoProductoRepository) {
		this.tipoProductoRepository = tipoProductoRepository;
	}

	@Override
	public List<TipoProductoDTO> listar() {
		return tipoProductoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<TipoProductoDTO> obtenerPorId(Long id) {
		return tipoProductoRepository.findById(id).map(this::toDTO);
	}

	@Override
	public Optional<TipoProductoDTO> obtenerPorNombre(String nombre) {
		return tipoProductoRepository.findByNombre(nombre).map(this::toDTO);
	}

	@Override
	public boolean existePorNombre(String nombre) {
		return tipoProductoRepository.existsByNombre(nombre);
	}

	@Override
	public TipoProductoDTO guardar(TipoProductoDTO dto) {
		if (existePorNombre(dto.getNombre())) {
			throw new RuntimeException("Ya existe un tipo de producto con ese nombre");
		}
		TipoProductoEntity entity = toEntity(dto);
		TipoProductoEntity saved = tipoProductoRepository.save(entity);
		return toDTO(saved);
	}

	@Override
	public TipoProductoDTO actualizar(TipoProductoDTO dto) {
		// Aquí podrías hacer una validación adicional si quieres
		TipoProductoEntity entity = toEntity(dto);
		TipoProductoEntity updated = tipoProductoRepository.save(entity);
		return toDTO(updated);
	}

	@Override
	public void eliminar(Long id) {
		tipoProductoRepository.deleteById(id);
	}

	// ==== Mappers ====

	private TipoProductoDTO toDTO(TipoProductoEntity entity) {
		return new TipoProductoDTO(entity.getId(), entity.getNombre());
	}

	private TipoProductoEntity toEntity(TipoProductoDTO dto) {
		return TipoProductoEntity.builder().id(dto.getId()).nombre(dto.getNombre()).build();
	}
}
