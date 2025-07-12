package pe.com.capince.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistritoDTO {

    private Long id;

    private String nombre;

    private boolean estado;
}
