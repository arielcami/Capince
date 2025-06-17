package pe.com.capince.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistritoDTO {

    private Long id;

    private String nombre;

    private boolean estado;
}
