package pe.com.capince.service;

import pe.com.capince.dto.EmpleadoDTO;
import pe.com.capince.entity.EmpleadoEntity;
import pe.com.capince.service.generic.ServiceGeneric;

import java.util.List;

public interface EmpleadoService extends ServiceGeneric<EmpleadoEntity, Long> {

    EmpleadoDTO saveDTO(EmpleadoDTO dto);
   
    EmpleadoDTO findByIdDTO(Long id);

    List<EmpleadoDTO> findAllDTO();
    
    EmpleadoDTO updateDTO(Long id, EmpleadoDTO dto);
    
    void deleteByIdDTO(Long id);
    
    void activar(Long id);
    void desactivar(Long id);

    
}
