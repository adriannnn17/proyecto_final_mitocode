package org.acme.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.domain.model.enums.EstadoReservaEnum;
import org.acme.infraestructure.db.converters.EstadoReservaEnumConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Reserva", schema = "dbo")
public class Reserva {

    @Id
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "HoraInicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "HoraFin", nullable = false)
    private LocalTime horaFin;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "ClienteId", referencedColumnName = "Id", nullable = false)
    private Cliente cliente;

    @jakarta.persistence.ManyToOne
    @jakarta.persistence.JoinColumn(name = "ProfesionalId", referencedColumnName = "Id", nullable = false)
    private Profesional profesional;

    @Convert(converter = EstadoReservaEnumConverter.class)
    @Column(name = "EstadoKd", nullable = false)
    private EstadoReservaEnum estado;

    @PrePersist
    public void persist() {
        if(isNull(id)) {
            id = UUID.randomUUID();
        }
    }
}
