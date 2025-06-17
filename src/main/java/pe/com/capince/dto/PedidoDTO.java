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

    private ClienteDTO cliente;

    private BigDecimal total;

    private EmpleadoDTO empleado;  // Puede ser null

    private Integer estadoPedido;

    private String direccionEntrega; // Puede ser null

    private LocalDateTime fechaPedido;

    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;

    private DeliveryDTO delivery;  // Puede ser null
}
