package pe.com.capince.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "delivery")
@PrimaryKeyJoinColumn(name = "id") // Clave primaria y FK a empleado.id
public class DeliveryEntity extends EmpleadoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "unidad", length = 25, nullable = false)
    private String unidad;

    @Column(name = "placa", length = 8, nullable = false, unique = true)
    private String placa;
}