	package pe.com.capince.service;

import java.util.List;

import pe.com.capince.dto.ClienteDTO;
import pe.com.capince.entity.ClienteEntity;
import pe.com.capince.service.generic.ServiceGeneric;

public interface ClienteService extends ServiceGeneric<ClienteEntity, Long> {
    
    List<ClienteDTO> findAllDTO();
    ClienteDTO findByIdDTO(Long id);
    ClienteDTO saveDTO(ClienteDTO dto);
    ClienteDTO updateDTO(ClienteDTO dto);
	
    List<ClienteDTO> findByDocumentoContainingDTO(String documentoFragment);

    void activar(Long id);
    void desactivar(Long id);

    
}
