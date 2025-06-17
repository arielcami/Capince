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
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pe.com.capince.dto.EmpleadoDTO;
import pe.com.capince.service.EmpleadoService;

@RestController
@RequestMapping("/api/empleado")
@RequiredArgsConstructor
public class EmpleadoRestController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> findAll() {
        return ResponseEntity.ok(empleadoService.findAllDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.findByIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> save(@RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(empleadoService.saveDTO(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Long id, @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(empleadoService.updateDTO(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empleadoService.deleteByIdDTO(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        empleadoService.activar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        empleadoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
    
}
