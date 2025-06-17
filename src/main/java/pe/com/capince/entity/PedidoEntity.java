package pe.com.capince.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "estado_pedido", nullable = false)
    private Integer estadoPedido;

    // Dirección de entrega (puede ser null)
    @Column(name = "direccion_entrega", length = 200)
    private String direccionEntrega;

    // Fecha del pedido (timestamp)
    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    // Delivery asignado (puede ser null)
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.creadoEn = now;
        this.actualizadoEn = now;
        if (this.fechaPedido == null) {
            this.fechaPedido = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }
}
