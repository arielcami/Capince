package pe.com.capince.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long id;

    private String nombre;

    private String tipoProducto;

    private Double precio;

    private Integer stock;

    private boolean estado;
}
