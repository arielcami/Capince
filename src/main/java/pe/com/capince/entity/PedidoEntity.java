package pe.com.capince.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class PedidoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente que hace el pedido (no nullable)
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    // Total del pedido
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Empleado asignado (puede ser null)
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private EmpleadoEntity empleado;

    // Estado del pedido (tinyint)
    @Column(name = "estado", nullable = false)
    private Byte estado;

    // Dirección de entrega (puede ser null)
    @Column(name = "direccion_entrega", length = 200)
    private String direccionEntrega;
    
    // Para llevar (TRUE) o para comer en el local (FALSE)
    @Column(name = "llevar")
    private boolean llevar;

    // Fecha del pedido (timestamp)
    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;


    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }
}
