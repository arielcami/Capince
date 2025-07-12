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
import pe.com.capince.dto.DistritoDTO;
import pe.com.capince.service.DistritoService;

@RestController
@RequestMapping("/api/distrito")
public class DistritoRestController {

	private final DistritoService distritoService;

	public DistritoRestController(DistritoService distritoService) {
		this.distritoService = distritoService;
	}

	@GetMapping
	public ResponseEntity<List<DistritoDTO>> listar() {
		return ResponseEntity.ok(distritoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DistritoDTO> obtenerPorId(@PathVariable Long id) {
		Optional<DistritoDTO> dto = distritoService.obtenerPorId(id);
		return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<DistritoDTO> guardar(@RequestBody DistritoDTO dto) {
		DistritoDTO creado = distritoService.guardar(dto);
		return ResponseEntity.ok(creado);
	}

	@PutMapping
	public ResponseEntity<DistritoDTO> actualizar(@RequestBody DistritoDTO dto) {
		DistritoDTO actualizado = distritoService.actualizar(dto);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		distritoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}