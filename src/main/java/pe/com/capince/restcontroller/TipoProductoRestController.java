package pe.com.capince.restcontroller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.capince.dto.TipoProductoDTO;
import pe.com.capince.service.TipoProductoService;

@RestController
@RequestMapping("/api/tipo-producto")
@CrossOrigin(origins = "*")
public class TipoProductoRestController {

	private final TipoProductoService tipoProductoService;

	public TipoProductoRestController(TipoProductoService tipoProductoService) {
		this.tipoProductoService = tipoProductoService;
	}

	@GetMapping
	public ResponseEntity<List<TipoProductoDTO>> listar() {
		return ResponseEntity.ok(tipoProductoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoProductoDTO> obtenerPorId(@PathVariable Long id) {
		Optional<TipoProductoDTO> dto = tipoProductoService.obtenerPorId(id);
		return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<TipoProductoDTO> obtenerPorNombre(@PathVariable String nombre) {
		Optional<TipoProductoDTO> dto = tipoProductoService.obtenerPorNombre(nombre);
		return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/existe/{nombre}")
	public ResponseEntity<Boolean> existePorNombre(@PathVariable String nombre) {
		return ResponseEntity.ok(tipoProductoService.existePorNombre(nombre));
	}

	@PostMapping
	public ResponseEntity<TipoProductoDTO> guardar(@RequestBody TipoProductoDTO dto) {
		TipoProductoDTO creado = tipoProductoService.guardar(dto);
		return ResponseEntity.ok(creado);
	}

	@PutMapping
	public ResponseEntity<TipoProductoDTO> actualizar(@RequestBody TipoProductoDTO dto) {
		TipoProductoDTO actualizado = tipoProductoService.actualizar(dto);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		tipoProductoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
