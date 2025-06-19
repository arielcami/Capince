package pe.com.capince.restcontroller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pe.com.capince.dto.PedidoDTO;
import pe.com.capince.service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoRestController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO dto) {
        PedidoDTO creado = pedidoService.crear(dto);
        return ResponseEntity.created(URI.create("/api/pedido/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable Long id, @RequestBody PedidoDTO dto) {
        PedidoDTO actualizado = pedidoService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.obtenerPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam Byte nuevoEstado) {
        pedidoService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok().build();
    }
}
