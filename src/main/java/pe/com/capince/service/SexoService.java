package pe.com.capince.service;

import java.util.List;
import java.util.Optional;
import pe.com.capince.dto.SexoDTO;

public interface SexoService {

	List<SexoDTO> listar();

	Optional<SexoDTO> obtenerPorId(Short id);

	SexoDTO guardar(SexoDTO dto);

	SexoDTO actualizar(SexoDTO dto);

	void eliminar(Short id);
}
