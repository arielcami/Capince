package pe.com.capince.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedidoDTO {
	private Integer id;
	private Integer pedidoId;
	private Integer productoId;
	private String productoNombre;
	private Integer cantidad;
	private BigDecimal precioUnitario;
	private String comentario;
	private boolean estado;
}
