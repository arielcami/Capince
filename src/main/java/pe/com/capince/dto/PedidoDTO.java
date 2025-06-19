package pe.com.capince.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoDTO {
	private Integer id;
	private Integer clienteId;
	private String clienteNombre;
	private BigDecimal total;
	private Integer empleadoId;
	private String empleadoNombre;
	private String direccionEntrega;
	private boolean llevar;
	private LocalDateTime fechaPedido;
	private Integer deliveryId;
	private String deliveryNombre;
	private Byte estado;
}
