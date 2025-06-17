package pe.com.capince.restcontroller;

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
import pe.com.capince.dto.ProductoDTO;
import pe.com.capince.service.ProductoService;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoRestController {

    private final ProductoService productoService;

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductoDTO> guardar(@RequestBody ProductoDTO dto) {
        ProductoDTO guardado = productoService.saveDTO(dto);
        return ResponseEntity.ok(guardado);
    }

    
    
    
    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        ProductoDTO dto = productoService.findByIdDTO(id);
        return ResponseEntity.ok(dto);
    }

    
    
    
    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> lista = productoService.findAllDTO();
        return ResponseEntity.ok(lista);
    }

    
    
    
    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        // dto.setId(id);
        ProductoDTO actualizado = productoService.updateDTO(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    
    
    
    
    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.deleteByIdDTO(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
    

    // PATCH: Cambiar estado del producto
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam boolean estado) {
        productoService.cambiarEstado(id, estado);
        return ResponseEntity.noContent().build();
    }
    
    
    
    

    // PATCH: Ajustar stock del producto
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> ajustarStock(@PathVariable Long id, @RequestParam int delta) {
        productoService.ajustarStock(id, delta);
        return ResponseEntity.noContent().build();
    }
}
