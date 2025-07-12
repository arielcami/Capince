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
import pe.com.capince.dto.SexoDTO;
import pe.com.capince.service.SexoService;

@RestController
@RequestMapping("/api/sexo")
public class SexoRestController {

	private final SexoService sexoService;

	public SexoRestController(SexoService sexoService) {
		this.sexoService = sexoService;
	}

	@GetMapping
	public ResponseEntity<List<SexoDTO>> listar() {
		return ResponseEntity.ok(sexoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SexoDTO> obtenerPorId(@PathVariable Short id) {
		Optional<SexoDTO> dto = sexoService.obtenerPorId(id);
		return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<SexoDTO> guardar(@RequestBody SexoDTO dto) {
		SexoDTO creado = sexoService.guardar(dto);
		return ResponseEntity.ok(creado);
	}

	@PutMapping
	public ResponseEntity<SexoDTO> actualizar(@RequestBody SexoDTO dto) {
		SexoDTO actualizado = sexoService.actualizar(dto);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Short id) {
		sexoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
