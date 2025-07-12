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
import pe.com.capince.dto.TipoDocumentoDTO;
import pe.com.capince.service.TipoDocumentoService;

@RestController
@RequestMapping("/api/tipo-documento")
public class TipoDocumentoRestController {

	private final TipoDocumentoService tipoDocumentoService;

	public TipoDocumentoRestController(TipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}

	@GetMapping
	public ResponseEntity<List<TipoDocumentoDTO>> listar() {
		return ResponseEntity.ok(tipoDocumentoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoDocumentoDTO> obtenerPorId(@PathVariable Long id) {
		Optional<TipoDocumentoDTO> dto = tipoDocumentoService.obtenerPorId(id);
		return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<TipoDocumentoDTO> guardar(@RequestBody TipoDocumentoDTO dto) {
		TipoDocumentoDTO creado = tipoDocumentoService.guardar(dto);
		return ResponseEntity.ok(creado);
	}

	@PutMapping
	public ResponseEntity<TipoDocumentoDTO> actualizar(@RequestBody TipoDocumentoDTO dto) {
		TipoDocumentoDTO actualizado = tipoDocumentoService.actualizar(dto);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		tipoDocumentoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
