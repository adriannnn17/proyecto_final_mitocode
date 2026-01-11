package org.acme.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.infraestructure.db.converters.EstadoActivoEnumConverter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HorarioDisponible", schema = "dbo")
public class HorarioDisponible {

    @Id
    @Column(name = "Id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ProfesionalId", referencedColumnName = "Id", nullable = false)
    private Profesional profesional;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "HoraInicio", nullable = false)
    @JdbcTypeCode(SqlTypes.TIME)
    private LocalTime horaInicio;

    @Column(name = "HoraFin", nullable = false)
    @JdbcTypeCode(SqlTypes.TIME)
    private LocalTime horaFin;

    @Convert(converter = EstadoActivoEnumConverter.class)
    @Column(name = "Estado", nullable = false)
    private EstadoActivoEnum estado;

    @PrePersist
    public void persist() {
        if (isNull(id)) {
            id = UUID.randomUUID();
        }
    }
}
