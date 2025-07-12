package pe.com.capince.service;

import java.util.List;
import java.util.Optional;
import pe.com.capince.dto.TipoDocumentoDTO;

public interface TipoDocumentoService {

	List<TipoDocumentoDTO> listar();

	Optional<TipoDocumentoDTO> obtenerPorId(Long id);

	TipoDocumentoDTO guardar(TipoDocumentoDTO dto);

	TipoDocumentoDTO actualizar(TipoDocumentoDTO dto);

	void eliminar(Long id);
}
