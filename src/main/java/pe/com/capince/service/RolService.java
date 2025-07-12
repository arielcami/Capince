package pe.com.capince.service;

import java.util.List;
import java.util.Optional;
import pe.com.capince.dto.RolDTO;

public interface RolService {

	List<RolDTO> listar();

	Optional<RolDTO> obtenerPorId(Long id);

	RolDTO guardar(RolDTO dto);

	RolDTO actualizar(RolDTO dto);

	void eliminar(Long id);
}
