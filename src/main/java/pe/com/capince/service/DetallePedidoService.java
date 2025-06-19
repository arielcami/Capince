package pe.com.capince.service;

import pe.com.capince.dto.DetallePedidoDTO;
import java.util.List;

public interface DetallePedidoService {

    DetallePedidoDTO crear(DetallePedidoDTO dto);

    DetallePedidoDTO actualizar(Long id, DetallePedidoDTO dto);

    DetallePedidoDTO obtenerPorId(Long id);

    List<DetallePedidoDTO> listarPorPedidoId(Long pedidoId);

    void eliminar(Long id);
    
    void cambiarEstado(Long pedidoId, Byte nuevoEstado);
    
    List<DetallePedidoDTO> listarTodos();

}
