package pe.com.capince.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pe.com.capince.dto.TipoDocumentoDTO;
import pe.com.capince.entity.TipoDocumentoEntity;
import pe.com.capince.repository.TipoDocumentoRepository;
import pe.com.capince.service.TipoDocumentoService;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	private final TipoDocumentoRepository tipoDocumentoRepository;

	public TipoDocumentoServiceImpl(TipoDocumentoRepository tipoDocumentoRepository) {
		this.tipoDocumentoRepository = tipoDocumentoRepository;
	}

	@Override
	public List<TipoDocumentoDTO> listar() {
		return tipoDocumentoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<TipoDocumentoDTO> obtenerPorId(Long id) {
		return tipoDocumentoRepository.findById(id).map(this::toDTO);
	}

	@Override
	public TipoDocumentoDTO guardar(TipoDocumentoDTO dto) {
		TipoDocumentoEntity entity = toEntity(dto);
		TipoDocumentoEntity saved = tipoDocumentoRepository.save(entity);
		return toDTO(saved);
	}

	@Override
	public TipoDocumentoDTO actualizar(TipoDocumentoDTO dto) {
		TipoDocumentoEntity entity = toEntity(dto);
		TipoDocumentoEntity updated = tipoDocumentoRepository.save(entity);
		return toDTO(updated);
	}

	@Override
	public void eliminar(Long id) {
		tipoDocumentoRepository.deleteById(id);
	}

	// ==== Mappers ====

	private TipoDocumentoDTO toDTO(TipoDocumentoEntity entity) {
		return TipoDocumentoDTO.builder().id(entity.getId()).nombre(entity.getNombre()).build();
	}

	private TipoDocumentoEntity toEntity(TipoDocumentoDTO dto) {
		return TipoDocumentoEntity.builder().id(dto.getId()).nombre(dto.getNombre()).build();
	}
}
