package pe.com.capince.service;

import pe.com.capince.dto.DistritoDTO;

import java.util.List;
import java.util.Optional;

public interface DistritoService {
	
	List<DistritoDTO> listar();

	Optional<DistritoDTO> obtenerPorId(Long id);

	DistritoDTO guardar(DistritoDTO dto);

	DistritoDTO actualizar(DistritoDTO dto);

	void eliminar(Long id);
}
