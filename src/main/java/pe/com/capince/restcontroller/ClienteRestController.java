package pe.com.capince.restcontroller;

import pe.com.capince.dto.ClienteDTO;
import pe.com.capince.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {

    private final ClienteService clienteService;

    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Postman: OK!
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAll() {
        List<ClienteDTO> clientes = clienteService.findAllDTO();
        return ResponseEntity.ok(clientes);
    }

    
    // Postman: OK!
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findByIdDTO(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    // Postman: OK! NECESITA CONFIGURAR EL POSTMAN 
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.saveDTO(clienteDTO);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO existingCliente = clienteService.findByIdDTO(id);
        if (existingCliente == null) {
            return ResponseEntity.notFound().build();
        }
        clienteDTO.setId(id);
        ClienteDTO updatedCliente = clienteService.updateDTO(clienteDTO);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        clienteService.activar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        clienteService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
        

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> findByDocumentoContaining(@RequestParam String documento) {
        List<ClienteDTO> clientes = clienteService.findByDocumentoContainingDTO(documento);
        return ResponseEntity.ok(clientes);
    }
}
