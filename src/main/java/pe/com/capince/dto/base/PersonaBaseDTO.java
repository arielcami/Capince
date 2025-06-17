package pe.com.capince.dto.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaBaseDTO {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String documento;
    private String tipoDocumento; // Podría ser String o un objeto
    private String sexo;
    private String direccion;
    private boolean estado;
}
