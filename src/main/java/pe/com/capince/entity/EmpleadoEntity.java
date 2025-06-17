package pe.com.capince.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pe.com.capince.entity.base.PersonaBase;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "empleado")
@Inheritance(strategy = InheritanceType.JOINED) // Pa poder hacer herencia con Delivery
public class EmpleadoEntity extends PersonaBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "distrito", nullable = false)
    private DistritoEntity distrito;

    @ManyToOne
    @JoinColumn(name = "rol", nullable = false)
    private RolEntity rol;

    @Column(name = "clave", nullable = false, length = 80)
    private String clave;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }
}
