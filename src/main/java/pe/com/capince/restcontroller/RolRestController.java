package pe.com.capince.restcontroller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.capince.dto.RolDTO;
import pe.com.capince.service.RolService;

@RestController
@RequestMapping("/api/rol")
public class RolRestController {

	private final RolService rolService;

	public RolRestController(RolService rolService) {
		this.rolService = rolService;
	}

	@GetMapping
	public ResponseEntity<List<RolDTO>> listar() {
		return ResponseEntity.ok(rolService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<RolDTO> obtenerPorId(@PathVariable Long id) {
		Optional<RolDTO> dto = rolService.obtenerPorId(id);
		return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<RolDTO> guardar(@RequestBody RolDTO dto) {
		RolDTO creado = rolService.guardar(dto);
		return ResponseEntity.ok(creado);
	}

	@PutMapping
	public ResponseEntity<RolDTO> actualizar(@RequestBody RolDTO dto) {
		RolDTO actualizado = rolService.actualizar(dto);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		rolService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
