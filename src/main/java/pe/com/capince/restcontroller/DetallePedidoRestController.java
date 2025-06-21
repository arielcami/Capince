package pe.com.capince.restcontroller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pe.com.capince.dto.DetallePedidoDTO;
import pe.com.capince.service.DetallePedidoService;

@RestController
@RequestMapping("/api/detalle-pedido")
@RequiredArgsConstructor
public class DetallePedidoRestController {

    private final DetallePedidoService detallePedidoService;
    
    @GetMapping // Postman: OK
    public ResponseEntity<List<DetallePedidoDTO>> listarTodos() {
        List<DetallePedidoDTO> detalles = detallePedidoService.listarTodos();
        return ResponseEntity.ok(detalles);
    }

    @PostMapping // Postman: OK
    public ResponseEntity<DetallePedidoDTO> crear(@RequestBody DetallePedidoDTO dto) {
        DetallePedidoDTO creado = detallePedidoService.crear(dto);
        return ResponseEntity.created(URI.create("/api/detalle-pedido/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}") // Postman: OK
    public ResponseEntity<DetallePedidoDTO> actualizar(@PathVariable Long id, @RequestBody DetallePedidoDTO dto) {
        DetallePedidoDTO actualizado = detallePedidoService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/{id}") // Postman: OK
    public ResponseEntity<DetallePedidoDTO> obtenerPorId(@PathVariable Long id) {
        DetallePedidoDTO detalle = detallePedidoService.obtenerPorId(id);
        return ResponseEntity.ok(detalle);
    }

    @GetMapping("/pedido/{pedidoId}") // Postman: OK
    public ResponseEntity<List<DetallePedidoDTO>> listarPorPedido(@PathVariable Long pedidoId) {
        List<DetallePedidoDTO> detalles = detallePedidoService.listarPorPedidoId(pedidoId);
        return ResponseEntity.ok(detalles);
    }

    @DeleteMapping("/{id}") // Postman: OK
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
