package pe.com.capince.service;

import java.util.List;
import java.util.Optional;
import pe.com.capince.dto.TipoProductoDTO;

public interface TipoProductoService {

	List<TipoProductoDTO> listar();

	Optional<TipoProductoDTO> obtenerPorId(Long id);

	Optional<TipoProductoDTO> obtenerPorNombre(String nombre);

	boolean existePorNombre(String nombre);

	TipoProductoDTO guardar(TipoProductoDTO dto);

	TipoProductoDTO actualizar(TipoProductoDTO dto);

	void eliminar(Long id);
}
