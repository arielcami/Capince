package pe.com.capince.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pe.com.capince.dto.SexoDTO;
import pe.com.capince.entity.SexoEntity;
import pe.com.capince.repository.SexoRepository;
import pe.com.capince.service.SexoService;

@Service
public class SexoServiceImpl implements SexoService {

	private final SexoRepository sexoRepository;

	public SexoServiceImpl(SexoRepository sexoRepository) {
		this.sexoRepository = sexoRepository;
	}

	@Override
	public List<SexoDTO> listar() {
		return sexoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<SexoDTO> obtenerPorId(Short id) {
		return sexoRepository.findById(id).map(this::toDTO);
	}

	@Override
	public SexoDTO guardar(SexoDTO dto) {
		SexoEntity entity = toEntity(dto);
		SexoEntity saved = sexoRepository.save(entity);
		return toDTO(saved);
	}

	@Override
	public SexoDTO actualizar(SexoDTO dto) {
		SexoEntity entity = toEntity(dto);
		SexoEntity updated = sexoRepository.save(entity);
		return toDTO(updated);
	}

	@Override
	public void eliminar(Short id) {
		sexoRepository.deleteById(id);
	}

	// === Mappers internos ===

	private SexoDTO toDTO(SexoEntity entity) {
		return SexoDTO.builder().id(entity.getId()).nombre(entity.getNombre()).build();
	}

	private SexoEntity toEntity(SexoDTO dto) {
		return SexoEntity.builder().id(dto.getId()).nombre(dto.getNombre()).build();
	}
}
