package pe.com.capince.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pe.com.capince.dto.DistritoDTO;
import pe.com.capince.entity.DistritoEntity;
import pe.com.capince.repository.DistritoRepository;
import pe.com.capince.service.DistritoService;

@Service
public class DistritoServiceImpl implements DistritoService {

	private final DistritoRepository distritoRepository;

	public DistritoServiceImpl(DistritoRepository distritoRepository) {
		this.distritoRepository = distritoRepository;
	}

	@Override
	public List<DistritoDTO> listar() {
		return distritoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<DistritoDTO> obtenerPorId(Long id) {
		return distritoRepository.findById(id).map(this::toDTO);
	}

	@Override
	public DistritoDTO guardar(DistritoDTO dto) {
		DistritoEntity entity = toEntity(dto);
		DistritoEntity saved = distritoRepository.save(entity);
		return toDTO(saved);
	}

	@Override
	public DistritoDTO actualizar(DistritoDTO dto) {
		DistritoEntity entity = toEntity(dto);
		DistritoEntity updated = distritoRepository.save(entity);
		return toDTO(updated);
	}

	@Override
	public void eliminar(Long id) {
		distritoRepository.deleteById(id);
	}

	// ==== Mappers ====

	private DistritoDTO toDTO(DistritoEntity entity) {
		return DistritoDTO.builder().id(entity.getId()).nombre(entity.getNombre()).build();
	}

	private DistritoEntity toEntity(DistritoDTO dto) {
		return DistritoEntity.builder().id(dto.getId()).nombre(dto.getNombre()).build();
	}
}
