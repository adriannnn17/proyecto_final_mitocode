package org.acme.infraestructure.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDto {
    private UUID id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private UUID clienteId;
    private UUID profesionalId;
    private String estado;
    private ClienteDto cliente;
    private ProfesionalDto profesional;
}

