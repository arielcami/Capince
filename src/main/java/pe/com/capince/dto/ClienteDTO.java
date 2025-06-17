package pe.com.capince.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.com.capince.dto.base.PersonaBaseDTO;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClienteDTO extends PersonaBaseDTO {
	
    private Long id;
    
    private String distrito;
    
}
