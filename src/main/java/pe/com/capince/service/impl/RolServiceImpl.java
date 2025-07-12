package pe.com.capince.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pe.com.capince.dto.RolDTO;
import pe.com.capince.entity.RolEntity;
import pe.com.capince.repository.RolRepository;
import pe.com.capince.service.RolService;

@Service
public class RolServiceImpl implements RolService {

	private final RolRepository rolRepository;

	public RolServiceImpl(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	@Override
	public List<RolDTO> listar() {
		return rolRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public Optional<RolDTO> obtenerPorId(Long id) {
		return rolRepository.findById(id).map(this::toDTO);
	}

	@Override
	public RolDTO guardar(RolDTO dto) {
		RolEntity entity = toEntity(dto);
		RolEntity saved = rolRepository.save(entity);
		return toDTO(saved);
	}

	@Override
	public RolDTO actualizar(RolDTO dto) {
		RolEntity entity = toEntity(dto);
		RolEntity updated = rolRepository.save(entity);
		return toDTO(updated);
	}

	@Override
	public void eliminar(Long id) {
		rolRepository.deleteById(id);
	}

	// === Mappers ===

	private RolDTO toDTO(RolEntity entity) {
		return RolDTO.builder().id(entity.getId()).nombre(entity.getNombre()).build();
	}

	private RolEntity toEntity(RolDTO dto) {
		return RolEntity.builder().id(dto.getId()).nombre(dto.getNombre()).build();
	}
}
