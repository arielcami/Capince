package pe.com.capince.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pe.com.capince.entity.TipoDocumentoEntity;
import pe.com.capince.entity.SexoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class PersonaBase {

    @Column(name = "nombre", length = 30, nullable = false)
    private String nombres;

    @Column(name = "apellido_paterno", length = 30, nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 30, nullable = false)
    private String apellidoMaterno;

    @Column(name = "telefono", length = 19, nullable = false)
    private String telefono;

    @Column(name = "documento", length = 12, nullable = false, unique = true)
    private String documento;

    @ManyToOne
    @JoinColumn(name = "tipo_documento", nullable = false)
    private TipoDocumentoEntity tipoDocumento;

    @ManyToOne
    @JoinColumn(name = "sexo", nullable = false)
    private SexoEntity sexo;

    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Column(name = "estado", nullable = false)
    private boolean estado;
}
