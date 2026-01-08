package org.acme.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.infraestructure.db.converters.EstadoActivoEnumConverter;

import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Profesional", schema = "dbo")
public class Profesional {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "Especialidad", nullable = false, length = 100)
    private String especialidad;

    @Convert(converter = EstadoActivoEnumConverter.class)
    @Column(name = "EstadoActivo", nullable = false)
    private EstadoActivoEnum estadoActivo;

    @PrePersist
    public void persist() {
        if(isNull(id)) {
            id = UUID.randomUUID();
        }
    }
}
