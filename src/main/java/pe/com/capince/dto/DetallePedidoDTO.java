package pe.com.capince.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedidoDTO {

    private Integer id;

    private PedidoDTO pedido;

    private ProductoDTO producto;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private boolean estado;

    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;
}
