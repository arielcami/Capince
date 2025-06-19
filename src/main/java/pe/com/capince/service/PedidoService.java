package pe.com.capince.service;

import pe.com.capince.dto.PedidoDTO;
import java.util.List;

public interface PedidoService {

    PedidoDTO crear(PedidoDTO dto);

    PedidoDTO actualizar(Long id, PedidoDTO dto);

    PedidoDTO obtenerPorId(Long id);

    List<PedidoDTO> listarTodos();

    void eliminar(Long id);

    void cambiarEstado(Long pedidoId, Byte nuevoEstado);
}
